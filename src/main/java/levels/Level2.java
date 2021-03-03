package levels;

import com.google.common.collect.Lists;
import equipment.items.QuestItem;
import mechanic.battle.*;
import mechanic.labyrinth.*;
import menu.*;
import units.Character;
import units.*;
import units.npcs.*;
import utils.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

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
            Character.getInstance().getInventory().addItem(new QuestItem("Бумажный свёрток"));
            System.out.println("Ты кладёшь бумажный свёрток к себе в инвентарь");
            track();
        });
        rollmenu.addItem("Оставить на месте", () -> {
            System.out.println("Ты решаешь что не стоит брать чужие вещи и решаешь вернуться на дорогу");
            track();
        });
        rollmenu.showAndChoose();

    }


    private void track() {
        System.out.println("Напевая песню про друзей, идущих по дороге летним днём, ты не замечаешь, как солнце уже село.\n" +
                "В голову приходит понимание, что необходимо найти место для ночлега.\n" +
                "В глаза бросается идеальное место у реки... Когда ты подходишь к этому месту, ты замечаешь его...");
        Utils.suspense(1000);
        Battler battler = Randomizer.randomize(
                new ObjectAndProbability<>(new Wolf(), 5),
                new ObjectAndProbability<>(new Goblin(), 5),
                new ObjectAndProbability<>(new Sanya(), 1),
                new ObjectAndProbability<>(new Spider(), 3),
                new ObjectAndProbability<>(new Skeleton(), 3),
                new ObjectAndProbability<>(new Gnoll(), 1)
        );
        Fight fight = new Fight(Character.getInstance(), battler);
        fight.battle();
        if (Character.getInstance().getCurrentHealth() <= 0) {
            System.out.println("Ты погиб так и не узнав, что за секреты таит в себе этот свёрток.\n" +
                    " Причиной твоей смерти стал '" + battler.getName() + "'");
            Utils.endGame();
        } else {
            System.out.println("Ты не знаешь почему, но ты уверен, что поблизости больше нет противников и позволяешь себе спокойно передохнуть.");
            Character.getInstance().setCurrentHealth(Character.getInstance().getMaxHealth()); //todo сделать метод SetFullRest
            Utils.suspense(2000);
            System.out.println("Наступает утро и ты решаешь продолжить своё путешествие\n" +
                    "Через пару часов ты доходишь до ворот города Даумгард");
            Utils.suspense(1000);
            daumgard();
        }

    }

    private void daumgard() {
        System.out.println("Подходя к воротам ты видишь мальчишку, что бежит к тебе \n" +
                "Он говорит тебе: \n" +
                "Волчица ждёт тебя в корчме Пивная кружка, поспеши, она не привыкла долго ждать.\n" +
                "Как только ты открываешь рот, чтобы спросить что здесь происходит, мальчишка уже убежал вглубь города");
        Utils.suspense(2000);
        System.out.println("Ты спрашиваешь у прохожих где найти эту корчму и отправляешься к ней\n" +
                "На входе Пивной кружки стоит громила...");
        Menu menu = new Menu("Что ты выберешь:");
        menu.addItem("Подойти к нему и сказать, что Волчица искала тебя", () -> {
                    System.out.println("Ты подходишь к громиле и говоришь, что тебя искала Волчица, его ответ немного тебя обескуражил:");
                    Utils.suspense(1000);
                    System.out.println("А ты купил DLC к Литориану? Просто там продолжение этого квеста, а без DLC туда не попасть, ты уж прости...");
                    Utils.endGame();
                }
        );
        menu.addItem("Попытаться просто зайти в кабак", () -> {
            System.out.println("С серьёзным лицом, не смотря в глаза громиле, ты просто пытаешься зайти внутрь, но понимаешь что это было ошибкой...\n" +
                    "Громила хватает тебя за руку и с силой выпихвает тебя наружу... :\n" +
                    "У нас вход платный, так что гони монету, иначе худо будет...");
            Utils.suspense(700);
            System.out.println("Чёрт, у нас же не реализована голда, похоже это конец...");
            Utils.endGame();
        });

        menu.showAndChoose();
    }
}