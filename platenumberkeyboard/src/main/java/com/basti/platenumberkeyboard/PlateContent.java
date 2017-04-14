package com.basti.platenumberkeyboard;

/**
 * 正文键盘Bean
 *
 * Created by Boateng17 on 2017/4/13.
 */

public class PlateContent {
    String label;//键盘上显示的文字
    String content;//实际的文字

    public PlateContent(String label, String content) {
        this.label = label;
        this.content = content;
    }

    public String getLabel() {

        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
