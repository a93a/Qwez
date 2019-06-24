package com.example.qwez.ui.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.qwez.R;
import com.example.qwez.base.BaseFragment;
import com.example.qwez.bus.RxBus;
import com.example.qwez.bus.event.NullEvent;
import com.example.qwez.entity.IntroData;
import com.example.qwez.util.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class IntroFragment extends BaseFragment {

    private IntroData data;
    public final static String INTRO_DATA = "intro data";

    @BindView(R.id.button_start_q)
    Button next;
    @BindView(R.id.cat_image)
    ImageView imageView;
    @BindView(R.id.cat)
    TextView cat;
    @BindView(R.id.diff)
    TextView diff;
    @BindView(R.id.answered_qs)
    TextView answered;

    @Override
    protected int getLayout() {
        return R.layout.fragment_intro;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //to to able to get around not having to override whole onCreateView method
        // from BaseFragment to bindviews before setting values on them here.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            IntroData data = getArguments().getParcelable(INTRO_DATA);
            cat.setText(data.getCategory());
            diff.setText(String.format("Difficulty: %s", data.getDifficulty()));
            answered.setText(String.format("Answered %s of 10 questions so far", data.getAnswered()));
            Glide.with(context)
                    .asBitmap()
                    .load(data.getPhotoUrl())
                    .into(imageView);
        }

        return view;
    }

    @OnClick(R.id.button_start_q)
    public void onClick(){
        ViewUtil.disableViewShort(next);
        RxBus.publish(RxBus.START_OR_END_QUIZ, new NullEvent());
    }

}
