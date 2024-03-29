package com.example.qwez.ui.start;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.databinding.ActivityStartBinding;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.ui.common.ItemDecorator;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.ui.start.recycler.CustomAdapter;
import com.example.qwez.ui.start.recycler.GameAdapter;
import com.example.qwez.ui.start.recycler.SwipeDeleteHelper;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.example.qwez.util.ViewUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class StartActivity extends BaseActivity<ActivityStartBinding>{

    @Inject
    StartVMFactory factory;
    StartViewModel viewModel;

    private GameAdapter adapter;

    /**
     * Create BaseActivity with {@code binding} layout binding
     *
     * @param binding the layout binding
     */
    public StartActivity(ActivityStartBinding binding) {
        super(binding);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new GameAdapter(this);
        binding.recyclerviewQuestions.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewQuestions.setAdapter(adapter);
        binding.recyclerviewQuestions.addItemDecoration(new ItemDecorator(ItemDecorator.MARGIN));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDeleteHelper(adapter, this));
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewQuestions);

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestion);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);
        viewModel.gameData().observe(this, this::onGames);
        viewModel.user().observe(this, this::onUser);
        viewModel.userPhotoUrl().observe(this, this::onPhoto);

        viewModel.prepare();

    }

    @Override
    protected void onStart() {
        super.onStart();

        RxBus.subscribe(RxBus.DELETE_GAME, this, o -> {
            GameEvent gameEvent = (GameEvent) o;
            Game deleteGame = gameEvent.getGame();
            MaterialDialog.Builder builder = CustomMaterialDialog.areYouSure("Delete game?", this)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        viewModel.deleteGame(deleteGame);
                    });
            showCustomDialog(builder);
        });

        RxBus.subscribe(RxBus.GAME_TOUCHED, this, o -> {
            GameEvent gameEvent = (GameEvent) o;
            Game gameTouched = gameEvent.getGame();
            adapter.setClickable(false);
            viewModel.openQuestion(this,gameTouched.getGameId());
        });

    }

    private void onPhoto(Uri uri){
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .apply(RequestOptions.circleCropTransform())    //do i really need this?? Cardview takes care of this?
                .into(binding.userImage);
    }

    private void onUser(String s) {
        binding.usernameDisplay.setText(s);
    }

    private void onGames(List<Game> games) {
        adapter.setData(games);
    }

    private void onProgess(Boolean aBoolean) {

    }

    private void onError(ErrorCarrier error) {
        showCustomDialog(CustomMaterialDialog.error("Error",this, "Something went wrong: "+error.getMessage()+". Please try again"));
    }

    private void onQuestion(Boolean addedQestion) {
        if(addedQestion){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Question added", this)
                    .onPositive((dialog, which) -> dialog.dismiss());
            showCustomDialog(builder);
        }
    }

    void onClick(){
        ViewUtil.disableViewShort(binding.buttonAddQuestion);

        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(R.layout.dialog_add_question, null);
        final LinearLayout layout = stdView.findViewById(R.id.add_question_layout);

        final Spinner cat = layout.findViewById(R.id.spinner_add_cat);
        final Spinner diff = layout.findViewById(R.id.spinner_add_diff);

        final TextView err = layout.findViewById(R.id.err_text);

        String choiceCat = getResources().getString(R.string.choose_a_category);
        String choiceDif = getResources().getString(R.string.choose_difficulty);

        final List<String> cats = Category.getList();
        cats.add(0,choiceCat);
        final CustomAdapter<String> catAdapter = new CustomAdapter<>(this, android.R.layout.simple_spinner_item,cats);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);

        final List<String> diffs = Difficulty.getList();
        diffs.add(0,choiceDif);
        final CustomAdapter<String> diffAdapter = new CustomAdapter<>(this, android.R.layout.simple_spinner_item,diffs);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diff.setAdapter(diffAdapter);

        MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Add a question", this, layout)
                .autoDismiss(false)
                .onPositive((dialog, which) -> {

                    String categoryString = (String) cat.getSelectedItem();
                    String difficultyString = (String) diff.getSelectedItem();

                    if(!categoryString.equals(choiceCat) && !difficultyString.equals(choiceDif)){
                        dialog.dismiss();
                        Category category = Category.getMap().get(categoryString);
                        Difficulty difficulty = Difficulty.getMap().get(difficultyString);
                        viewModel.getQuestion(category, difficulty);
                    }else{
                        err.setText(getResources().getText(R.string.err_text_dialog));
                        err.setVisibility(View.VISIBLE);
                    }
                })
                .onNegative((dialog, which) -> dialog.dismiss());
        showCustomDialog(builder);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_meny, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUser();
        adapter.setClickable(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewUtil.disableMenuItemShort(item);
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.start_menu_settings:
                viewModel.openSettings(this);
                break;
            case R.id.start_menu_highscore:
                viewModel.openHighscore(this);
                break;
        }
        return true;
    }

}
