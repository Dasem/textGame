package levels;

import equipment.items.*;
import mechanic.battle.*;
import mechanic.labyrinth.*;
import menu.*;
import units.Character;
import units.npcs.*;
import utils.*;

import java.lang.*;

public class Level1 implements Levelable {

    @Override
    public void startLevel() {
        Menu startMenu = new Menu("Хочешь ли ты продолжать путешествие?:", false);
        startMenu.addItem("Выжить", () -> {
            System.out.println("Вы видите перед собой карту и поднимаете её");

            Labyrinth startLabyrinth = new Labyrinth();
            startLabyrinth.enterLabyrinth();
            System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
            Menu menu = new Menu("Перед тобой развилка с путевым знаком, на нём видны варианты, выбери дальнейший путь:");
            menu.addItem("Ривергард", () -> {
                rivergard();
                System.out.println("Перед твоим взором расстилаются огромные ворота города Ривергард");
            });
            menu.addItem("Литориан", () -> {
                litorian();
            });
            menu.addItem("Зачарованный лес", () -> {
                enchantedForest();
            });
            menu.showAndChoose();

            System.out.println("Поздравляю! Ты закончил первый уровень. Вот тебе плюшки.");
        });
        startMenu.addItem("Умереть", () -> {
            System.out.println("Вы умерли, слава герою " + Character.getInstance().getUsername());
        });
        startMenu.showAndChoose();
    }

    private void rivergard() {
        System.out.println("На своём пути к Ривергарду, ты видишь одинокого гоблина...");
        Utils.suspense(1500);
        System.out.println("Кажется, начинается битва:");
        Fight fight = new Fight(Character.getInstance(), new Goblin());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            System.out.println("Ты убит гоблином. пресс F");
            Utils.endGame();
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
            Utils.suspense();
            System.out.println("После недолгих раздумий ты берёшь с собой голову гоблина");
            Character.getInstance().getInventory().addItem(new UselessItem("Голова гоблина"));
        }
    }

    private void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private void enchantedForest() {
        System.out.println(
                "Уверенно шагая по лесной тропинке ты чувствуешь на себе чей-то взгляд.\n" +
                "По спине пробежал холодок.\n" +
                "Ты решаешь перейти на бег, но коварные корни деревьев цепляются тебе за ноги и ты кубарем катишься вниз, в глубь леса.\n" +
                "Встав и отряхнувшись ты видишь перед собой развилку...");
        Menu menu = new Menu("Куда ты отравишься?");
        menu.addItem("Влево", () -> {
            Labyrinth labyrinth = new Labyrinth();
            labyrinth.enterLabyrinth();
        });
        menu.addItem("Вправо", () -> {
            System.out.println("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf();
        });
        menu.showAndChoose();
    }

    private void fightWithWolf() {
        Fight fight = new Fight(Character.getInstance(), new Wolf());
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            System.out.println("Ты убит волком. пресс F");
            Utils.endGame();
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }
}