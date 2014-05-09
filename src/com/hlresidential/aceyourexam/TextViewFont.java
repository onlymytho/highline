package com.hlresidential.aceyourexam;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewFont extends TextView {
	 
    public TextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

   public TextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

   public TextViewFont(Context context) {
        super(context);
   }


   public void setTypeface(Typeface tf, int style) {
         if (style == Typeface.BOLD) {
              super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/MYRIADPRO-BOLD.OTF")/*, -1*/);
          } else if (style == Typeface.ITALIC) {
              super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/MYRIADPRO-IT.OTF")/*, -1*/);
          } else {
             super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/MYRIADPRO-REGULAR.OTF")/*, -1*/);
          }
    }
}
