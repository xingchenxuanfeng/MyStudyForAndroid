package com.study.flybirddemo.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.study.flybirddemo.R;
import com.study.flybirddemo.spirit.Spirit;

public class Drawer extends Thread {
	Context context;
	int width, height;
	SurfaceHolder holder;
	Paint paint;
	boolean keep = false;
	public boolean isdrop = false;
	public int gameStatus;
	public int pipeSpeed = -5;
	public final static int ready = 0;
	public final static int running = 1;
	public final static int stop = 2;
	public Spirit bg, floor, bird, getready, tap, pipeTop1, pipeTop2,
			pipeBottom1, pipeBottom2, gameover, panel;
	private Bitmap pipeBottom_bm;
	private Bitmap pipeTop_bm;
	private int pipeSpan;
	private int pipeTopHeight1;
	private int pipeTopHeight2;
	private int score;
	private boolean pass1;
	private boolean pass2;

	public boolean isKeep() {
		return keep;
	}

	public void setKeep(boolean keep) {
		this.keep = keep;
	}

	// private Canvas canvas = null;

	public Drawer() {
		// TODO Auto-generated constructor stub
	}

	public Drawer(Context context, int width, int height, SurfaceHolder holder) {
		super();
		this.context = context;
		this.width = width;
		this.height = height;
		this.holder = holder;
		this.paint = new Paint();
		keep = true;
		paint.setColor(Color.YELLOW);
		paint.setTextSize(50f);
		initSpirit();
		initAttr();
	}

	public void initAttr() {
		score = 0;
		pass1 = false;
		pass2 = false;
		isdrop = false;
		pipeSpan = height / 4;
		pipeTopHeight1 = height / 4;
		pipeTopHeight2 = height / 3;
		getready.setXY(width / 2 - getready.getWidth() / 2, height / 3-getready.getHeight());
		tap.setXY(width / 2 - tap.getWidth() / 2, height / 2-tap.getHeight()/2);
		bird.setXY(width / 5, height / 3);
		bird.setYspeed(0);
		pipeTop1.setXY(width, 0);
		pipeBottom1.setXY(width, pipeTopHeight1 + pipeSpan);
		pipeTop2.setXY(width * 3 / 2, 0);
		pipeBottom2.setXY(width * 3 / 2, pipeTopHeight2 + pipeSpan);
		gameover.setXY(width / 2 - gameover.getWidth() / 2, height / 4);
		panel.setXY(width / 2 - panel.getWidth() / 2,
				height / 2 - panel.getHeight() / 2);
	}

	public void initSpirit() {
		// TODO Auto-generated method stub
		Bitmap bg_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.bg);
		bg = new Spirit(bg_bm);
		Bitmap floor_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ground);
		floor = new Spirit(floor_bm);
		Bitmap getready_bm = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.getready);
		getready = new Spirit(getready_bm);
		Bitmap tag_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.tag);
		tap = new Spirit(tag_bm);
		Bitmap[] bird_bms = new Bitmap[] {
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.bird),
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.bird2),
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.bird3) };
		bird = new Spirit(bird_bms);
		pipeTop_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pipe2);
		pipeTop1 = new Spirit(pipeTop_bm);
		pipeTop2 = pipeTop1.getClone();
		pipeBottom_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pipe1);
		pipeBottom1 = new Spirit(pipeBottom_bm);
		pipeBottom2 = pipeBottom1.getClone();
		Bitmap gameover_bm = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.gameover);
		gameover = new Spirit(gameover_bm);
		Bitmap panel_bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.panel);
		panel = new Spirit(panel_bm);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (keep) {
			Canvas canvas = null;
			try {
				if (canvas == null) {
					canvas = holder.lockCanvas();
					drawGameView(canvas);
				}

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
		super.run();
	}

	/**
	 * @author XC 绘制游戏视图
	 * @param canvas
	 * @param canvas
	 */
	public void drawGameView(Canvas canvas) {
		canvas.drawBitmap(bg.getBitmap(), null, new Rect(0, 0, width, height),
				paint);
		canvas.drawBitmap(floor.getBitmap(), null, new Rect(0, height * 3 / 4,
				width, height), paint);
		switch (gameStatus) {
		case ready:
			drawReadyView(canvas);
			break;
		case running:
			drawRunningView(canvas);
			break;
		case stop:
			drawStopView(canvas);
			break;
		default:
			break;
		}
	}

	private void drawStopView(Canvas canvas) {
		// TODO Auto-generated method stub
		gameover.drawSelf(canvas);
		// panel.drawSelf(canvas);
		canvas.drawText("您的分数为" + score, width / 2 - 150, height / 2, paint);

	}

	private void drawRunningView(Canvas canvas) {
		// TODO Auto-generated method stub
		try {
			if (!isdrop) {
				// 管子属性设置
				pipeTop1.setX(pipeTop1.getX() + pipeSpeed);
				pipeTop1.setBitmap(Bitmap.createBitmap(pipeTop_bm, 0,
						pipeTop_bm.getHeight() - pipeTopHeight1,
						pipeTop1.getWidth(), pipeTopHeight1));
				pipeBottom1.setXY(pipeTop1.getX(), pipeTopHeight1 + pipeSpan);
				pipeBottom1.setBitmap(Bitmap.createBitmap(pipeBottom_bm, 0, 0,
						pipeBottom1.getWidth(), height * 3 / 4 - pipeTopHeight1
								- pipeSpan));
				pipeTop2.setX(pipeTop2.getX() + pipeSpeed);
				pipeTop2.setBitmap(Bitmap.createBitmap(pipeTop_bm, 0,
						pipeTop_bm.getHeight() - pipeTopHeight2,
						pipeTop2.getWidth(), pipeTopHeight2));
				pipeBottom2.setXY(pipeTop2.getX(), pipeTopHeight2 + pipeSpan);
				pipeBottom2.setBitmap(Bitmap.createBitmap(pipeBottom_bm, 0, 0,
						pipeBottom2.getWidth(), height * 3 / 4 - pipeTopHeight2
								- pipeSpan));
			}
			// 判断出界
			checkOutBound(pipeBottom1, true);
			checkOutBound(pipeTop2, false);
			checkOutBound(pipeBottom2, false);
			checkOutBound(pipeTop1, true);
			// 管子绘制
			pipeTop1.drawSelf(canvas);
			pipeBottom1.drawSelf(canvas);
			pipeTop2.drawSelf(canvas);
			pipeBottom2.drawSelf(canvas);
			// 鸟速度
			bird.setYspeed(bird.getYspeed() + 1.3f);
			bird.setY(bird.getY() + bird.getYspeed());
			if (bird.getY() < 0) {
				bird.setY(0);
			}
			// 碰撞检测
			if (checkIsHit(pipeTop1, pipeTop2, pipeBottom1, pipeBottom2)) {
				isdrop = true;
			}
			if (bird.getY() + bird.getHeight() >= height * 3 / 4) {
				gameStatus = stop;
				bird.setY(height * 3 / 4 - bird.getHeight());
			}
			if (bird.getX() > pipeTop1.getX() && !pass1) {
				pass1 = true;
				score++;
			}
			if (bird.getX() > pipeTop2.getX() && !pass2) {
				pass2 = true;
				score++;
			}
			// 鸟绘制
			bird.drawTheBird(canvas);
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkIsHit(Spirit... spirits) {
		// TODO Auto-generated method stub
		for (Spirit spirit : spirits) {
			if (spirit.getRectF().intersect(bird.getRectF()))
				return true;
		}
		return false;
	}

	private void checkOutBound(Spirit spirit, boolean b) {
		// TODO Auto-generated method stub
		if (spirit.getX() + spirit.getWidth() < 0) {
			spirit.setX(width);
			if (b) {
				pipeTopHeight1 = (int) (height * (Math.random() * 2 + 1) / 8);
				pass1 = false;
			} else {
				pipeTopHeight2 = (int) (height * (Math.random() * 2 + 1) / 8);
				pass2 = false;
			}
		}
	}

	private void drawReadyView(Canvas canvas) {
		// TODO Auto-generated method stub
		getready.drawSelf(canvas);
		tap.drawTheBird(canvas);
		bird.drawTheBird(canvas);
	}
}
