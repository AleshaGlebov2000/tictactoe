import java.util.Random;
import java.util.Scanner;


public class Cross {
    public static int SIZE = 5;
    public static int DOTS_TO_WIN = 4;
    public static final char DOT_EMPTY = '.';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил SkyNet");
                break;
            }

            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");

    }
    // Отрисовка поля
    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + "|");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map [i][j] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

//Проверка ячейки пустая/полная
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map [y][x] = DOT_X;
    }



    public static void aiTurn() {
        int x, y;

        //блокировка хода человека
        for (int v = 0; v < SIZE; v++) {
            for (int h = 0; h < SIZE; h++) {
                if (h + DOTS_TO_WIN <= SIZE) { // по горизонтали
                    if (checkLineHorisont(v, h, DOT_X) == DOTS_TO_WIN - 1) {
                        if (MoveAiLineHorisont(v, h, DOT_O)) return;
                    }
                    if (v - DOTS_TO_WIN > -2) { //вверх по диагонали
                        if (checkDiaDown(v, h, DOT_X) == DOTS_TO_WIN - 1) {
                            if (MoveAiDiaUp(v, h, DOT_O)) return;
                        }
                    }
                    if (v + DOTS_TO_WIN <= SIZE) {  // Вниз по диагонали
                        if (checkDiaDown(v, h, DOT_X) == DOTS_TO_WIN - 1) {
                            if (MoveAiDiaDown(v, h, DOT_O)) return;
                        }
                    }
                }
            }
        }

        do {x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[y][x] = DOT_O;
    }

    private static boolean MoveAiLineHorisont(int v, int h, char DOT_O) {
     for (int j = h; j < DOTS_TO_WIN; j ++) {
         if ((map[v][j] == DOT_EMPTY)) {
             map[v][j] = DOT_O;
             return true;
         }
     }
     return false;
    }
    private static boolean MoveAiVertical(int v, int h, char DOT_O) {
        for (int i = v; i < DOTS_TO_WIN; i++) {
            if ((map[i][h] == DOT_EMPTY)) {
                map[i][h] = DOT_O;
                return true;
            }
        }
        return  false;
    }

    private static boolean MoveAiDiaDown(int v, int h, char DOT_O) {
        for (int i = v; i < DOTS_TO_WIN; i++) {
            if ((map[i+v][i+h] == DOT_EMPTY)) {
                map[i+v][i+h] = DOT_O;
                return true;
            }
        }
        return  false;
    }

    private static boolean MoveAiDiaUp(int v, int h, char DOT_O) {
        for (int i = v; i < DOTS_TO_WIN; i++) {
            if ((map[v-i][h+i] == DOT_EMPTY)) {
                map[v-i][h+i] = DOT_O;
                return true;
            }
        }
        return  false;
    }

    public static boolean checkWin(char symb) {
       for (int v = 0; v < SIZE; v++) {
           for (int h = 0; h < SIZE; h++) {
               if (h + DOTS_TO_WIN <= SIZE) {
                   if (checkLineHorisont(v, h, symb) >= DOTS_TO_WIN) return true;

              if (v - DOTS_TO_WIN > -2) {
                  if (checkDiaUp(v, h, symb) >= DOTS_TO_WIN) return true;
                    }

               if (v + DOTS_TO_WIN <= SIZE) {
                   if (checkDiaDown(v, h, symb) >= DOTS_TO_WIN) return true;
               }
           }
           if (v + DOTS_TO_WIN <= SIZE) {
               if (checkLineVertical(v, h, symb) >= DOTS_TO_WIN) return true;
            }
       }
    }
       return false;

}

    public static int checkLineHorisont(int v, int h, char symb) {
int count = 0;
for (int j = h; j < DOTS_TO_WIN +h; j++) {
    if((map[v][j]==symb)) count++;
        }
       return count;
    }
public static int checkLineVertical(int v, int h, char symb) {
        int count = 0;
        for (int i = v; i < DOTS_TO_WIN +v; i++) {
            if((map[i][h]==symb)) count++;
        }
        return count;
       }
public static int checkDiaUp(int v, int h, char symb) {
        int count = 0;
        for (int i = 0; i < DOTS_TO_WIN ; i++) {
            if((map[v-i][h+i]==symb)) count++;
        }
        return count;
       }
    private static int checkDiaDown(int v, int h, char symb) {


        int count = 0;
        for (int i = 0; i < DOTS_TO_WIN ; i++) {
            if((map[v][h]==symb)) count++;
        }
        return count;

    }


    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

}