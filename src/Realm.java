import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {
    private static BufferedReader br;

    private static FantasyCharacter player = null;

    private static BattleScene battleScene = null;


    public static void main(String[] args) {

        br = new BufferedReader(new InputStreamReader(System.in));

        battleScene = new BattleScene();

        System.out.println("Введите имя персонажа:");

        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {

        if (player == null) {
            player = new Hero(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("Спасти наш мир от драконов вызвался %s! Да будет его броня крепка и бицепс кругл!", player.getName()));

            printNavigation();

        }

        switch (string) {
            case "1": {
                merchMenu();

                printNavigation();
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3": {
                commitFightDragon();
            }
            break;
            case "4":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
                break;
            }
            case "зелье": {
                player.setHealthPoints(100);
                player.setGold(player.getGold() - 20);
                System.out.println("Прикупил здоровья " + player.getName() + "  " + player.getHealthPoints());
                System.out.println("Золота осталось " + player.getGold());
                printNavigation();
            }

        }

        command(br.readLine());
    }

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Сразиться с драконом");
        System.out.println("4. Выход");
    }


    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {
                System.out.println("Скелеты с гоблинами вас замочили");
                System.exit(1);
            }
        });
    }

    private static void commitFightDragon() {
        battleScene.fight(player, createDragon(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {
                System.out.println("Дракон вас замочил");

                System.exit(1);
            }
        });
    }


    private static void merchMenu() {
        if (player.getGold() == 0) {
            System.out.println("Извини братан,ты пока не заработал золота");

        } else if (player.getGold() < 20) {
            System.out.println("Золота на покупку здоровья недостаточно");

        } else {
            System.out.println("Купить здоровье,введите зелье");
            try {
                command(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    interface FightCallback {
        void fightWin();

        void fightLost();
    }

    private static FantasyCharacter createMonster() {

        int random = (int) (Math.random() * 10);

        if (random % 2 == 0) return new Goblin(
                "Гоблин",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }

    private static FantasyCharacter createDragon() {

        return new Dragon(
                "Дракон",
                850,
                25,
                20,
                300,
                2000
        );
    }
}
