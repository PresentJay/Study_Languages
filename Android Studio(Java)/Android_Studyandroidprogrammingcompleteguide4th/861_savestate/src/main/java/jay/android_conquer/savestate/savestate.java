package jay.android_conquer.savestate;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class savestate extends AppCompatActivity {
    private MyView vw;
    int x;
    int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            x=50;
        }else{
            x=savedInstanceState.getInt("x");
        }
        SharedPreferences pref = getSharedPreferences("SaveState",0);
        y = pref.getInt("y",50);
        vw = new MyView(this);
        vw.setFocusable(true);
        vw.setFocusableInTouchMode(true);
        setContentView(vw);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("SaveState", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("y", y);
        edit.commit();
    }

    protected class MyView extends View {
        public MyView(Context context){
            super(context);
        }

        public void onDraw(Canvas canvas){
            Paint p = new Paint();
            p.setColor(Color.GREEN);
            canvas.drawCircle(x, y, 16, p);
        }

        public boolean onKeyDown(int KeyCode, KeyEvent event){
            super.onKeyDown(KeyCode, event);
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                switch (KeyCode){
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        x-= 15;
                        invalidate();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        x+= 15;
                        invalidate();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        y-=15;
                        invalidate();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        y+=15;
                        invalidate();
                        return true;

                }
            }
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (event.getAction()==MotionEvent.ACTION_DOWN){
                x += 15;
                y += 15;
                invalidate();
                return true;
            }
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("x",x);
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        x = outState.getInt("x");
    }
}
