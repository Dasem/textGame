package levels;

import mechanic.battle.*;
import mechanic.labyrinth.*;
import menu.*;
import units.*;
import units.Character;
import utils.*;

import java.lang.*;

public class Level1 implements Levelable {

    @Override
    public void startLevel(units.Character character) {
        Menu startMenu = new Menu("Хочешь ли ты продолжать путешествие?:");
        startMenu.addItem("Выжить", () -> {
            System.out.println("Вы видите перед собой карту и поднимаете её");
            Labyrinth startLabyrinth = new Labyrinth();
            startLabyrinth.enterLabyrinth(character);
            System.out.println("Ура! Лабиринт пройден! Перед тобой открылись просторы древнего мира!");
            Menu menu = new Menu("Перед тобой развилка с путевым знаком, на нём видны варианты, выбери дальнейший путь:");
            menu.addItem("Ривергард", () -> {
                rivergard(character);
                System.out.println("Перед твоим взором расстилаются огромные ворота города Ривергард");
            });
            menu.addItem("Литориан", () -> {
                litorian();
            });
            menu.addItem("Зачарованный лес", () -> {
                enchantedForest(character);
            });
            menu.showAndChoose();

            System.out.println("Поздравляю! Ты закончил первый уровень. Вот тебе плюшки.");
        });
        startMenu.addItem("Умереть", () -> {
            System.out.println("Вы умерли, слава герою " + character.getUsername());
        });
        startMenu.showAndChoose();
    }

    private void rivergard(units.Character character) {
        System.out.println("На своём пути к Ривергарду, ты видишь одинокого гоблина...");
        Utils.suspense(1500);
        System.out.println("Кажется, начинается битва:");
        Fight fight = new Fight(character, new Goblin());
        fight.battle();
        if (character.getCurrentHealth() <=0) {
            System.out.println("Ты убит гоблином. пресс F");
            System.exit(0);
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }

    private void litorian() {
        System.out.println("Ты в литориане, но тут пусто, купи DLC, всего за 49,99$");
    }

    private void enchantedForest(Character character) {
        System.out.println("Уверенно шагая по лесной тропинке ты чувствуешь на себе чей-то взгляд.\nПо спине пробежал холодок.\nТы решаешь перейти на бег, но коварные корни деревьев цепляются тебе за ноги и ты кубарем катишься вниз, в глубь леса.\nВстав и отряхнувшись ты видишь перед собой развилку...   ");
        Menu menu = new Menu ("Куда ты отравишься?");
        menu.addItem("Влево",() -> {
            Labyrinth labyrinth = new Labyrinth();
            labyrinth.enterLabyrinth(character);
        });

        menu.addItem("Вправо",() -> {
            System.out.println("Перед тобой появляется волк с явно недружелюбными намерениями\n");
            fightWithWolf(character);
        });
        menu.showAndChoose();
    }

    private void fightWithWolf(Character character) {
        Fight fight = new Fight(character, new Wolf());
        fight.battle();
        if (character.getCurrentHealth() <=0) {
            System.out.println("Ты убит волком. пресс F");
            System.exit(0);
        } else {
            System.out.println("Бой дался тебе нелегко, но ты чувствуешь в себе силы двигаться дальше");
        }
    }
}