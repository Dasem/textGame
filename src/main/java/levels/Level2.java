package levels;

import mechanic.battle.*;
import mechanic.labyrinth.*;
import menu.*;
import units.Character;
import units.*;
import utils.*;

public class Level2 implements Levelable {

    @Override
    public void startLevel() {
        System.out.println("Идя по тропе ты натыкаешься на одиноко стоящий дом");
        Menu menu = new Menu("Что ты выберешь: ");
        menu.addItem("Подойти", () -> {
            aloneHouse();
        });
        menu.addItem("Пройти мимо", () -> {
            track();
        });
        menu.showAndChoose();
    }

    private void aloneHouse() {
        System.out.println("Ты подходишь к двери и видишь, что дверь приоткрыта");
        Menu housemenu = new Menu("Что ты выберешь: ");
        housemenu.addItem("Постучать", () -> {
            roomInside();
        });
        housemenu.addItem("Открыть дверь", () -> {
            roomInside();
        });
        housemenu.showAndChoose();

    }

    private void roomInside() {
        System.out.println("Почти дотянувшись до двери она волшебным образом отворяется и ты видишь перед собой пустую комнату\n" +
                "Ты замечаешь свёрток бумаги, лежащий на полу и решаешь посмотреть, что в нём");
        Utils.suspense(1500);
        System.out.println("Развернув свёрток ты видишь порошок синего цвета и кулон с волчьей пастью");
        Menu rollmenu = new Menu("Что ты выберешь: ");
        rollmenu.addItem("Взять его", () -> {
            // TODO: 26.02.2021 Добавить порошок
            track();
        });
        rollmenu.addItem("Оставить на месте", () -> {
            System.out.println("Ты решаешь что не стоит брать чужие вещи и решаешь вернуться на дорогу");
            track();
        });
        rollmenu.showAndChoose();

    }


    private void track() {
        System.out.println("Напевая песню про друзей, идущих по дороге летним днём ты не замечаешь как уже садится солнце\n" +
                "В голову приходит понимание, что необходимо найти место для ночлега");

    }
}


