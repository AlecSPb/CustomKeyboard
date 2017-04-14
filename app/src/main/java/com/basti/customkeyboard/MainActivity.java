package com.basti.customkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.widget.EditText;

import com.basti.platenumberkeyboard.OnHeaderViewClick;
import com.basti.platenumberkeyboard.PlateContent;
import com.basti.platenumberkeyboard.PlateContentKeyboard;
import com.basti.platenumberkeyboard.PlateProvinceNumberKeyboard;

public class MainActivity extends AppCompatActivity {
    PlateContentKeyboard plateContentKeyboard;
    PlateProvinceNumberKeyboard plateProvinceNumberKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.plate_edit_et);
        plateProvinceNumberKeyboard = (PlateProvinceNumberKeyboard) findViewById(R.id.province_keyboard);
        plateProvinceNumberKeyboard.setContentTv(editText);

        final EditText contentEditText = (EditText) findViewById(R.id.content_edit_et);
        plateContentKeyboard = (PlateContentKeyboard) findViewById(R.id.content_keyboard);
        plateContentKeyboard.setContentTv(contentEditText);
        plateContentKeyboard.setOnHeaderViewClick(new OnHeaderViewClick() {
            @Override
            public void onClick(int position, PlateContent plateContent) {
                Editable editable = contentEditText.getText();
                int start = contentEditText.getSelectionStart();
                editable.insert(start, plateContent.getContent());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        plateContentKeyboard.showKeyboard(false);
        plateProvinceNumberKeyboard.showKeyboard(false);

        return super.onTouchEvent(event);
    }
}
