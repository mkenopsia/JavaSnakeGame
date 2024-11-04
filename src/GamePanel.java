import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static int DELAY = 175;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH/UNIT_SIZE);
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 4;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running=false;
    Timer timer;
    Random random;
    Color color1= new Color(46, 46, 46, 255);
    Color color2= new Color(63, 62, 62, 255);

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //Задаётся размер окна через объект Dimension
        this.setBackground(Color.black);
        this.setFocusable(true); //Разрешается брать фокус и обрабатывать события устройств ввода
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); //Обновляется игровое поле
        draw(g);                 //Рисуется всё необходимое на очищенном полотне
    }
    public void draw(Graphics g){
        if (running) {
            for (int i=0;i<SCREEN_HEIGHT;i++) {
                for (int j = 0; j < SCREEN_WIDTH / UNIT_SIZE; j++) {
                    if (j % 2 == 1 && i % 2 == 0) {
                        g.setColor(color1);
                        g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    } else if (j % 2 == 0 && i % 2 == 0) {
                        g.setColor(color2);
                        g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    } else if (j % 2 == 1 && i % 2 == 1) {
                        g.setColor(color2);
                        g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    } else if (j % 2 == 0 && i % 2 == 1) {
                        g.setColor(color1);
                        g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //Раскрашивается голова по координатам юнитов
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //Раскрашиваются остальные части
                }
            }
            g.setColor(new Color(255, 144, 36));
            g.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, SCREEN_WIDTH-(metrics.stringWidth("Score:1000")),30);
        }
        else gameOver(g);

    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //В одну пару координат на поле по юнитам
        appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;  //спавнится яблоко

    }
    public void move(){
        for (int i = bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY) {
            ++applesEaten;
            ++bodyParts;
            --DELAY;
            newApple();
        }
    }
    public void checkCollisions(){
        //Столкновение с телом
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0]==y[i])){ //Если количество юнитов в голове
                running=false;                  //равно количеству юнитов в одном из сегментов = столкновение

            }
        }
        //Столкновение с границами
        if(x[0] < 0 ) running=false;                    //Если юнитов меньше нуля или больше чем на границе -
        else if (x[0] > SCREEN_WIDTH) running=false;    //вышли за одну из границ
        else if(y[0] < 0) running = false;
        else if (y[0] > SCREEN_HEIGHT) running = false;

        if (!running) timer.stop();
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH)/2,SCREEN_HEIGHT*2/3);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); //Вызывается paintComponent для обновления полей
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            //Считывается направление движения с клавиатуры (клавиши-стрелки)
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction !='R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction!='L') direction='R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction !='D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;
            }
        }
    }
}
