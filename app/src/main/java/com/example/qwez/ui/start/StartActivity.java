package com.example.qwez.ui.start;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.qwez.R;
import com.example.qwez.base.BaseActivity;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.GameEvent;
import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.repository.local.Game;
import com.example.qwez.repository.local.Question;
import com.example.qwez.ui.dialog.CustomMaterialDialog;
import com.example.qwez.ui.start.recycler.QuestionAdapter;
import com.example.qwez.ui.start.recycler.SwipeDeleteHelper;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        setContentView(R.layout.layout_start);

        ButterKnife.bind(this);

        adapter = new QuestionAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeDeleteHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel = ViewModelProviders.of(this,factory).get(StartViewModel.class);
        viewModel.questions().observe(this, this::onQuestions);
        viewModel.error().observe(this, this::onError);
        viewModel.progress().observe(this, this::onProgess);
        viewModel.gameData().observe(this, this::onGames);
        viewModel.user().observe(this, this::onUser);

        viewModel.prepare();

        RxBus.subscribe(RxBus.DELETE_GAME, this, o -> {
            GameEvent gameEvent = (GameEvent) o;
            Game game = gameEvent.getGame();
            MaterialDialog.Builder builder = CustomMaterialDialog.areYouSure("Delete game?", this)
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        viewModel.deleteGame(game);
                    });
            showCustomDialog(builder);
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

    private void onQuestions(List<Question> questions) {
        MaterialDialog.Builder builder = CustomMaterialDialog.okDialog("Question added", this)
                .onPositive((dialog, which) -> dialog.dismiss());
        showCustomDialog(builder);
    }

    @OnClick(R.id.button_add_question)
    void onClick(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(R.layout.dialog_add_question, null);
        LinearLayout layout = (LinearLayout) stdView.findViewById(R.id.add_question_layout);

        Spinner cat = (Spinner) layout.findViewById(R.id.spinner_add_cat);
        Spinner diff = (Spinner) layout.findViewById(R.id.spinner_add_diff);

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Category.getList());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);

        ArrayAdapter<String> diffAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Difficulty.getList());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diff.setAdapter(diffAdapter);

        MaterialDialog.Builder builder = CustomMaterialDialog.addQuestion("Add a question", this, layout)
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    String categoryString = (String) cat.getSelectedItem();
                    Category category = Category.getMap().get(categoryString);
                    String difficultyString = (String) diff.getSelectedItem();
                    Difficulty difficulty = Difficulty.getMap().get(difficultyString);
                    viewModel.getQuestion(category, difficulty);
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
