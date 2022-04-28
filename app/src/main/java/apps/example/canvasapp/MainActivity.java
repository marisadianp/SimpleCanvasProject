package apps.example.canvasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;

    private int mColorBackground;
    private int mColorRectangle;

    private final static int OFFSET = 120;
    private int mOffset = OFFSET;

    private final static int MULTIPLIER = 100;
    private Rect mRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.myimageview);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        mPaintText.setTextSize(70);
        mRec = new Rect();

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vWidth = view.getWidth();
                int vHeight = view.getHeight();
                int halfWidth = vWidth/2;
                int halfHeight = vHeight/2;

                if (mOffset==OFFSET){
                    mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
                    mImageView.setImageBitmap(mBitmap);

                    mCanvas = new Canvas(mBitmap);
                    mCanvas.drawColor(mColorBackground);
                    mCanvas.drawText("Keep Tapping", 100,100, mPaintText);
                    mOffset += OFFSET;
                }
                else {
                    if (mOffset < halfWidth && mOffset < halfHeight){
                        mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);

                        mRec.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                        mCanvas.drawRect(mRec, mPaint);
                        mOffset += OFFSET;
                    }
                    else {
                        int mColorAccent = ResourcesCompat.getColor(getResources(), R.color.teal_200, null);
                        mPaint.setColor(mColorAccent);
                        mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/3, mPaint);

                        String text = "Done!";
                        Rect mBonds = new Rect();
                        mPaintText.getTextBounds(text, 0, text.length(), mBonds);
                        int x = halfWidth - mBonds.centerX();
                        int y = halfHeight - mBonds.centerY();

                        mCanvas.drawText(text, x, y, mPaintText);
                    }

                }

                view.invalidate();

            }
        });
    }
}