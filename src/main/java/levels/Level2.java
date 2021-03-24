package levels;

import items.grocery.QuestItem;
import mechanic.battle.*;
import menu.*;
import units.character.Character;
import units.enemies.*;
import utils.*;
import utils.random.ObjectAndProbability;
import utils.random.Randomizer;

public class Level2 implements Levelable {

    @Override
    public void startLevel() {
        System.out.println("Идя по тропе ты натыкаешься на одиноко стоящий дом");
        LorMenu menu = new LorMenu("Что ты выберешь: ");
        menu.addItem("Подойти", this::aloneHouse);
        menu.addItem("Пройти мимо", this::track);
        menu.showAndChoose();
    }

    private void aloneHouse() {
        Utils.lor("Ты подходишь к двери и видишь, что дверь приоткрыта");
        LorMenu housemenu = new LorMenu("Что ты выберешь: ");
        housemenu.addItem("Постучать", this::roomInside);
        housemenu.addItem("Открыть дверь", this::roomInside);
        housemenu.showAndChoose();
    }

    private void roomInside() {
        Utils.lor("Почти дотянувшись до двери она волшебным образом отворяется и ты видишь перед собой пустую комнату\n" +
                "Ты замечаешь свёрток бумаги, лежащий на полу и решаешь посмотреть, что в нём");
        Utils.lor("Развернув свёрток ты видишь порошок синего цвета и кулон с волчьей пастью");
        LorMenu rollmenu = new LorMenu("Что ты выберешь: ");
        rollmenu.addItem("Взять его", () -> {
            Character.getInstance().lootItem(new QuestItem("Бумажный свёрток", 1));
            Utils.lor("Ты кладёшь бумажный свёрток к себе в инвентарь");
            track();
        });
        rollmenu.addItem("Оставить на месте", () -> {
            Utils.lor("Ты решаешь что не стоит брать чужие вещи и решаешь вернуться на дорогу");
            track();
        });
        rollmenu.showAndChoose();

    }


    private void track() {
        Utils.lor("Напевая песню про друзей, идущих по дороге летним днём, ты не замечаешь, как солнце уже село.\n" +
                "В голову приходит понимание, что необходимо найти место для ночлега.\n" +
                "В глаза бросается идеальное место у реки... Когда ты подходишь к этому месту, ты замечаешь его...");
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
            Utils.lor("Ты погиб так и не узнав, что за секреты таит в себе этот свёрток.\n" +
                    " Причиной твоей смерти стал '" + battler.getName() + "'");
            Utils.endGame();
        } else {
            Utils.lor("Ты не знаешь почему, но ты уверен, что поблизости больше нет противников и позволяешь себе спокойно передохнуть.");
            Character.getInstance().setCurrentHealth(Character.getInstance().getMaxHealth());
            Utils.lor("Наступает утро и ты решаешь продолжить своё путешествие\n" +
                    "Через пару часов ты доходишь до ворот города Даумгард");
            daumgard();
        }

    }

    private void daumgard() {
        Utils.lor("Подходя к воротам ты видишь мальчишку, что бежит к тебе \n" +
                "Он говорит тебе: \n" +
                "Волчица ждёт тебя в корчме Пивная кружка, поспеши, она не привыкла долго ждать.\n" +
                "Как только ты открываешь рот, чтобы спросить что здесь происходит, мальчишка уже убежал вглубь города");
        Utils.lor("Ты спрашиваешь у прохожих где найти эту корчму и отправляешься к ней\n" +
                "На входе Пивной кружки стоит громила...");
        LorMenu menu = new LorMenu("Что ты выберешь:");
        menu.addItem("Подойти к нему и сказать, что Волчица искала тебя", () -> {
                    Utils.lor("Ты подходишь к громиле и говоришь, что тебя искала Волчица:");
                    Utils.lor("Он молча кивает и указывает тебе на дверь возле стойки корчмаря");
                    bitchwolf();

                }
        );
        menu.addItem("Попытаться просто зайти в кабак", () -> {
            Utils.lor("С серьёзным лицом, не смотря в глаза громиле, ты просто пытаешься зайти внутрь, но понимаешь что это было ошибкой...\n" +
                    "Громила хватает тебя за руку и с силой выпихвает тебя наружу... :\n" +
                    "У нас вход платный, так что гони монету, иначе худо будет...");
            Utils.lor("Чёрт, у нас же не реализована голда, похоже это конец...");
            Utils.endGame();
        });
        menu.showAndChoose();
    }

    private void bitchwolf() {
        Utils.lor("Открыв дверь, ты видишь узкую лестницу, ведущую вверх. " +
                "Почти поднявшись на верх до вас доносится очень громкий звук удара о какой-то предмет и приближающиеся к вам шаги.");
        Utils.lor("Большая дубовая дверь открывается и перед вашим взором является фигура в плаще, которая направляется к тебе...");
        LorMenu menu = new LorMenu("Что ты выберешь");
        menu.addItem("Стоять на месте", () -> {
            Utils.lor("Явно разъярённая личность толкает тебя плечом и ,что-то бормоча себя под нос, спускается вниз");
            Character.getInstance().takeDamage(1);
            Utils.lor("Было немого больно...");
            bitchwolfQuest();
        });
        menu.addItem("Подвинуться", () -> {
            Utils.lor("Явно разъярённая проходит мимо тебя и ,что-то бормоча себя под нос, спускается вниз");
            bitchwolfQuest();
        });
        menu.showAndChoose();

    }

    private void bitchwolfQuest() {
        QuestItem scroll = Character.getInstance().findQuestItemByInventory(1); //todo доделать сайдквест
        Utils.lor("Ты видишь перед собой женщину и она тебе что-то говорит... ");
        if (scroll != null) {
            LorMenu menu = new LorMenu("Что ты выберешь");
            menu.addItem("Отдать", () -> {
                Character.getInstance().getInventory().removeItem(scroll);
                Utils.lor("Ты отдаёшь свёрток и что-то происходит");
            });
            menu.addItem("Не отдавать", () -> {
                Utils.lor("Ты не отдаёшь свёрток и просходит что-то, отличное от первого");
            });
            menu.showAndChoose();

        }
    }
}

