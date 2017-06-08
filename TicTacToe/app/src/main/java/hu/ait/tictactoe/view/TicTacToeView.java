package hu.ait.tictactoe.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.ait.tictactoe.MainActivity;
import hu.ait.tictactoe.R;
import hu.ait.tictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private Paint paintBg = null;
    private Paint paintLine = null;
    private Bitmap bitmapBg = null;

    private Paint paintFont = null;

    private PointF tmpPos = null;

    public TicTacToeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintFont = new Paint();
        paintFont.setTextSize(50);
        paintFont.setColor(Color.RED);

        bitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, w, h, false);

        paintFont.setTextSize(getHeight() / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
        canvas.drawBitmap(bitmapBg, 0, 0, null);

        drawGameArea(canvas);

        drawPlayers(canvas);

        canvas.drawText("H", 0, getHeight()/3, paintFont);

        if (tmpPos != null) {
            if (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) {
                canvas.drawCircle(tmpPos.x, tmpPos.y, getWidth() / 6, paintLine);
            } else {
                canvas.drawLine(tmpPos.x-getWidth()/6, tmpPos.y-getHeight()/6,
                    tmpPos.x+getWidth()/6, tmpPos.y+getHeight()/6, paintLine);
                canvas.drawLine(tmpPos.x-getWidth()/6, tmpPos.y+getHeight()/6,
                        tmpPos.x+getWidth()/6, tmpPos.y-getHeight()/6, paintLine);
            }
        }
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);

        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(), paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(), paintLine);

        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3, paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(), 2 * getHeight() / 3, paintLine);
    }

    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintLine);

                } else if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3, paintLine);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            tmpPos = new PointF(event.getX(), event.getY());
            invalidate();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            tmpPos = null;

            int x = (int)event.getX() / (getWidth()/3);
            int y = (int)event.getY() / (getHeight()/3);

            if (TicTacToeModel.getInstance().getFieldContent(x, y) == TicTacToeModel.EMPTY) {
                TicTacToeModel.getInstance().setFieldContent(x, y, TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().switchPlayers();

                invalidate();

                String next = (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) ? "O" : "X";
                ((MainActivity)getContext()).showMessage("The next player is: "+next);
            } else {
                ((MainActivity)getContext()).showMessage("You can not step here");
            }
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        TicTacToeModel.getInstance().resetGame();
        invalidate();
    }
}
