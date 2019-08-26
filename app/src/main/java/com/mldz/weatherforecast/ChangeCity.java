package com.mldz.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mldz.weatherforecast.mvp.model.ChangeCityModel;
import com.mldz.weatherforecast.mvp.presenter.ChangeCityPresenter;
import com.mldz.weatherforecast.mvp.view.ChangeCityView;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;

/**
 * Created by Maxim Zubarev on 2019-08-26.
 */
public class ChangeCity extends AppCompatActivity implements ChangeCityView {
    private ChangeCityPresenter presenter;
    private RadioGroup radioGroup;
    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);

        radioGroup = findViewById(R.id.radioGroup);
        btnOk = findViewById(R.id.ok);
        btnOk.setOnClickListener(okClick);

        presenter = new ChangeCityPresenter(new ChangeCityModel(this), new CompositeDisposable());
        presenter.bind(this);
        presenter.onCreate();
    }

    View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton myRadioButton = findViewById(checkedRadioButtonId);
            Intent intent = new Intent();
            String value;
            if (myRadioButton != null)
                value = (String) myRadioButton.getText();
            else
                value = null;
            intent.putExtra("city", value);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    public void setData(List<String> list) {
        for (String s : list) {
            RadioButton button = new RadioButton(this);
            String name = s.substring(0, 1).toUpperCase() + s.substring(1);
            button.setText(name);
            radioGroup.addView(button);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
