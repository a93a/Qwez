package com.example.qwez.ui.start;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.Game;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.ui.start.recycler.CustomAdapter;
import com.example.qwez.ui.start.recycler.GameAdapter;
import com.example.qwez.ui.start.recycler.ItemDecorator;
import com.example.qwez.ui.start.recycler.SwipeDeleteHelper;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class StartActivity extends BaseActivity{

    @Inject
    StartVMFactory factory;
    StartViewModel viewModel;

    @BindView(R.id.button_add_question)
    FloatingActionButton button;
    @BindView(R.id.recyclerview_questions)
    RecyclerView recyclerView;

    private GameAdapter adapter;

    private static final String NO_CAT_SELECTION = "Category";
    private static final String NO_DIFF_SELECTION = "Difficulty";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new GameAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDecorator(ItemDecorator.MARGIN));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDeleteHelper(adapter, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestion);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);
        viewModel.gameData().observe(this, this::onGames);
        viewModel.user().observe(this, this::onUser);

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

    private void onUser(String s) {
        setTitle(s);
    }

    private void onGames(List<Game> games) {
        adapter.setData(games);
    }

    private void onProgess(Boolean aBoolean) {

    }

    private void onError(ErrorCarrier error) {
        showCustomDialog(CustomMaterialDialog.error("Error",this, "Something went wrong: "+error.message+". Please try again"));
    }

    private void onQuestion(Boolean addedQestion) {
        if(addedQestion){
            MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Question added", this)
                    .onPositive((dialog, which) -> dialog.dismiss());
            showCustomDialog(builder);
        }
    }

    @OnClick(R.id.button_add_question)
    void onClick(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(R.layout.dialog_add_question, null);
        final LinearLayout layout = stdView.findViewById(R.id.add_question_layout);

        final Spinner cat = layout.findViewById(R.id.spinner_add_cat);
        final Spinner diff = layout.findViewById(R.id.spinner_add_diff);

        final TextView err = layout.findViewById(R.id.err_text);

        final List<String> cats = Category.getList();
        cats.add(0,"Category");
        final CustomAdapter<String> catAdapter = new CustomAdapter<>(this, android.R.layout.simple_spinner_item,cats);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);

        final List<String> diffs = Difficulty.getList();
        diffs.add(0,"Difficulty");
        final CustomAdapter<String> diffAdapter = new CustomAdapter<>(this, android.R.layout.simple_spinner_item,diffs);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diff.setAdapter(diffAdapter);

        MaterialDialog.Builder builder = CustomMaterialDialog.customDialog("Add a question", this, layout)
                .autoDismiss(false)
                .onPositive((dialog, which) -> {

                    String categoryString = (String) cat.getSelectedItem();
                    String difficultyString = (String) diff.getSelectedItem();

                    if(!categoryString.equals(NO_CAT_SELECTION) && !difficultyString.equals(NO_DIFF_SELECTION)){
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
        adapter.setClickable(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_start;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.start_menu_settings:
                viewModel.openSettings(this);
                break;
        }
        return true;
    }

}
