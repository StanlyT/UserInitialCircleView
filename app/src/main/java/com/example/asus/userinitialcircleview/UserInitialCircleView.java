package com.example.asus.userinitialcircleview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class UserInitialCircleView extends View {
    private static final int DEFAULT_INITIAL_COLOR = Color.GRAY;
    private static final int DEFAULT_STROKE_COLOR = Color.DKGRAY;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.DKGRAY;
    private static final String DEFAULT_INITIAL = "OK";
    private static final boolean DEFAULT_SHOW_INITIAL = true;
    private static final float DEFAULT_INITIAL_SIZE = 25f;
    private static final int DEFAULT_VIEW_SIZE = 96;
    private static final float DEFAULT_FILL_RADIUS = 0.9f;
    private static final float DEFAULT_STROKE_WIDTH = 5f;

    private int initialColor = DEFAULT_INITIAL_COLOR;
    private int shadowColor = DEFAULT_STROKE_COLOR;
    private int strokeColor = DEFAULT_BACKGROUND_COLOR;
    private int fillColor = DEFAULT_FILL_COLOR;
    private String initialText = DEFAULT_INITIAL;
    private float textSize = DEFAULT_INITIAL_SIZE;
    private float strokeWidth = DEFAULT_STROKE_WIDTH;
    private float fillRadius = DEFAULT_FILL_RADIUS;
    private boolean showInitial = DEFAULT_SHOW_INITIAL;

    private TextPaint initialTextPaint;

    private Paint shadowPaint;
    private Paint strokePaint;
    private Paint fillPaint;

    private RectF rectF;

    private int viewSize;

    public UserInitialCircleView(Context context) {
        super(context);
        init(null, 0);
    }

    public UserInitialCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public UserInitialCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.UserInitialCircleView, defStyle, 0);
        if (typedArray.hasValue(R.styleable.UserInitialCircleView_initial_text_circle_view)) {
            initialText = typedArray.getString(R.styleable.UserInitialCircleView_initial_text_circle_view);
        }

        initialColor = typedArray.getColor(R.styleable.UserInitialCircleView_initial_color_circle_view, DEFAULT_INITIAL_COLOR);
        fillColor = typedArray.getColor(R.styleable.UserInitialCircleView_inner_color_circle_view, DEFAULT_FILL_COLOR);
        strokeColor = typedArray.getColor(R.styleable.UserInitialCircleView_main_stroke_color_circle_view, DEFAULT_BACKGROUND_COLOR);
        shadowColor = typedArray.getColor(R.styleable.UserInitialCircleView_shadow_color_circle_view, DEFAULT_STROKE_COLOR);

        textSize = typedArray.getDimension(R.styleable.UserInitialCircleView_initial_size_circle_view, DEFAULT_INITIAL_SIZE);

        strokeWidth = typedArray.getFloat(R.styleable.UserInitialCircleView_stroke_width_circle_view, DEFAULT_STROKE_WIDTH);
        fillRadius = typedArray.getFloat(R.styleable.UserInitialCircleView_fill_radius_circle_view, DEFAULT_FILL_RADIUS);

        typedArray.recycle();

        // initial TextPaint
        initialTextPaint = new TextPaint();
        initialTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        initialTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        initialTextPaint.setTextAlign(Paint.Align.CENTER);
        initialTextPaint.setLinearText(true);
        initialTextPaint.setColor(initialColor);
        initialTextPaint.setTextSize(textSize);

        // stroke Paint
        shadowPaint = new Paint();
        shadowPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setColor(shadowColor);
        shadowPaint.setStrokeWidth(strokeWidth);

        // background Paint
        strokePaint = new Paint();
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(strokeColor);

        // fill Paint
        fillPaint = new Paint();
        fillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(fillColor);

        rectF = new RectF();
    }

    private void invalidateTextPaints() {
        initialTextPaint.setColor(initialColor);
        initialTextPaint.setTextSize(textSize);
        invalidate();
    }

    private void invalidatePaints() {
        strokePaint.setColor(strokeColor); // Color.GRAY
        shadowPaint.setColor(shadowColor);
        fillPaint.setColor(fillColor);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        viewSize = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.set(0, 0, viewSize, viewSize);
        rectF.offset((getWidth() - viewSize) / 2, (getHeight() - viewSize) / 2);

        final int halfBorder = (int) (shadowPaint.getStrokeWidth() / 2f + 0.5f);

        rectF.inset(halfBorder, halfBorder);

        float centerX = rectF.centerX();
        float centerY = rectF.centerY();

        canvas.drawArc(rectF, 0, 360, true, strokePaint);

        float radius = (viewSize / 2) * fillRadius;

        canvas.drawCircle(centerX, centerY, radius + 0.5f - shadowPaint.getStrokeWidth(), fillPaint);

        int positionX = (int) centerX;
        int positionY = (int) (centerY - (initialTextPaint.descent() + initialTextPaint.ascent()) / 2);
        canvas.drawOval(rectF, shadowPaint);

        if (showInitial) {
            canvas.drawText(initialText, positionX, positionY, initialTextPaint);
        }
    }

    /**
     *
     * @param title The example string attribute value to use.
     */
    public void setInitialText(String title) {
        initialText = title;
        invalidate();
    }

    /**
     *
     * @param shadowColor The stroke color attribute value to use.
     */
    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        invalidatePaints();
    }

    /**
     *
     * @param strokeColor The background color attribute value to use.
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidatePaints();
    }

    /**
     *
     * @param fillColor The fill color attribute value to use.
     */
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        invalidatePaints();
    }

    /**
     *
     * @param fillRadius The fill radius attribute value to use.
     */
    public void setFillRadius(float fillRadius) {
        this.fillRadius = fillRadius;
        invalidate();
    }

    /**
     *
     * @param titleSize The title size dimension attribute value to use.
     */
    public void setInitialSize(float titleSize) {
        textSize = titleSize;
        invalidateTextPaints();
    }


    /**
     *
     * @param initialColor The title text color attribute value to use.
     */
    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
        invalidateTextPaints();
    }


    /**
     *
     * @return The stroke width dimension attribute value.
     */
    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     *
     * @return The stroke color attribute value.
     */
    public int getShadowColor() {
        return shadowColor;
    }

    /**
     *
     * @return The background color attribute value.
     */
    public int getStrokeColor() {
        return strokeColor;
    }

    /**
     *
     * @return The fill color attribute value.
     */
    public int getFillColor() {
        return shadowColor;
    }

    /**
     *
     * @return The fill radius dimension attribute value.
     */
    public float getFillRadius() {
        return fillRadius;
    }
    /**
     *
     * @return The title size dimension attribute value.
     */
    public float getInitialSize() {
        return textSize;
    }

    /**
     *
     * @return The text color attribute value.
     */
    public int getInitialColor() {
        return initialColor;
    }

    /**
     *
     * @return The title string attribute value.
     */
    public String getInitialText() {
        return initialText;
    }

    /**
     *
     * @param strokeWidth The stroke width attribute value to use.
     */
    public void setStrokeColor(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }
}