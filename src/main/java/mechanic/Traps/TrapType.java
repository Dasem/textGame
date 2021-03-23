package mechanic.Traps;

import com.google.common.collect.Sets;
import mechanic.dice.*;
import units.character.Stat;


import java.util.Set;

public enum TrapType {

    STRENGTH_EASY_TRAP("Легкая ловушка на силу") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 10;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }

        public int trapDamage() {
            return (1 + Dice.D4.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nПол внезапно уходит из под ваших ног и вы проваливаетесь в яму. " +
                    "\nБлаго тут не глубоко, и вы способны выбраться. Больше это ловушка не застугнет вас в расплох.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВы замечаете на полу какой-то скрытый механизм и решаете не наступать на эту область. " +
                    "\nКажется, придётся прыгать.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы разбегаетесь и прыгаете так далеко, как только можете, красиво группируясь по приземлении. " +
                    "\nПосле чего вы замечаете на стене рычаг и нажимаете на него. Кажется, ловушка отключилась.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы разбегаетесь и прыгаете так далеко, как только можете, но этого не хватает, и вы приземляетесь раньше, чем хотели. " +
                    "\nПол тут же уходит вниз и вы падаете. К счастью, вы допускали такой исход и успели сгруппироваться, так что падение не было чересчур болезненным." +
                    "\nВыбравшись на другую сторону, вы находите несколько кем-то любезно оставленных досок и прокидываете их через пропасть. Так вы сможете безопасно вернуться.";
        }

        public boolean reverse() {
            return false;
        }
    },

    STRENGTH_MEDIUM_TRAP("Средняя ловушка на силу") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }

        public int trapDamage() {
            return (1 + Dice.D4.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nВнезапно, потолочный блок прямо над вами падает вам на голову. " +
                    "\nЧего смеёшься? Это больно! Хорошо хоть он больше на вас не упадёт.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВы замечаете, что следующая часть потолка находится чуть ниже остальных.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы разбегаетесь и прыгаете, преодолевая участок максимально быстро. " +
                    "\nПозади вас с грохотом падает каменная плита. Вы в безопасности.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы прыгаете не достаточно далеко, и плита падает прямо на вас. " +
                    "\nК счастью вы успеваете её подхватить и кое-как выпозти из под неё. Ваши руки оччень болят, но хотя бы вы живы.";
        }

        public boolean reverse() {
            return false;
        }
    },

    STRENGTH_HARD_TRAP("Сложная ловушка на силу") {
        public int trapDifficulty() {
            return 15;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.STRENGTH;
        }

        public int trapDamage() {
            return (1 + Dice.D6.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nВнезапно в потолке над вами открывается люк и на вас падает груда камней. " +
                    "\nСпустя какое-то время, вы кое-как выбрались из под завала, абсолютно без сил.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВы замечаете какой-то люк на потолке впереди. " +
                    "\nВы можете тригернуть ловушку с безопасного расстояния, либо вернуться и попробовать другой путь.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы кидаете небольшой булыжник чуть вперёд и в тот же момент люк открывается и из него падет груда камней. " +
                    "\nВы потратили несколько часов, разбирая завал. Вы очень устали, но живы, а это уже плюс.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы кидаете небольшой булыжник чуть вперёд и в тот же момент люк открывается и из него падет груда камней. " +
                    "\nВы разбираете примерно половину завала  как вдруг вас поражает резкая боль в спине. Кажется, вы её потянули. " +
                    "\nРазбирать завал вы дальше не сможете. Придётся искать обход.";
        }

        public boolean reverse() {
            return true;
        }

    },

    AGILITY_EASY_TRAP("Легкая ловушка на ловкость") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 10;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }

        public int trapDamage() {
            return (1 + Dice.D4.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nВнезапно из стен в вас вылетает залп стрел. " +
                    "\nВы даже смогли увернуться от некоторых, но всё равно получили порезы. " +
                    "\nКажется, это были все стрелы, что оставались в этих стенах. Вы можете идти дальше.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВы замечаете в стенах подозрительные отверстия. Кажется, вам тут не рады.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы аккуратно наступаете на пол возле ловушек и ничего не происходит. " +
                    "\nТогда вы решаете проползти под ловушками. Вы медленно вытирате пол своей одеждой и в результате оказываетесь на другом конце ловушки. " +
                    "\nПосле чего вы замечаете на стене рычаг и нажимаете на него. Кажется, ловушка отключилась.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы аккуратно наступаете на пол возле ловушек и ничего не происходит. " +
                    "\nТогда вы решаете проползти под ними. Вы спокойно ползёте под ловушками, но в какой-то момент вы чешете голову и ловушка срабатывает. " +
                    "\nВы лежали на полу, так что вас почти не задело, но осадочек от вашей глупости остался =(.";
        }

        public boolean reverse() {
            return false;
        }
    },

    AGILITY_MEDIUM_TRAP("Средняя ловушка на ловкость") {
        public int trapDifficulty() {
            return 10;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }

        public int trapDamage() {
            return (1 + Dice.D4.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nВнезапно из прорезей в стену (которые вы не заметили) вылетают качающеися под потолком лезвия. " +
                    "\nВы пытаетесь увернуться, но получается у вас плохо. Впредь надо быть внимательнее.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВы замечате в стенах и потолке подозрительные прорези, и быстро понимаете что к чему. Это лезвие-маятник.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы ловко и красиво пропрыгиваете по ловушке и после каждого прыжка за вами пролетает огромное лезвие. " +
                    "\nЗаключительное сальто - и вы уже приземляетесь в паре сантиметров после заключительного лезвия " +
                    "и кланяетесь несуществующей публике, которая несёт вам несуществующие цветы. " +
                    "\nСмахнув фантазии о славе, вы продолжаете путь.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы пытаетесь предугадать размер и скорость полета лезвия, но получается у вас не очень. " +
                    "\nВы поранились и пообещали себе поупражняться в ловкости.";
        }

        public boolean reverse() {
            return false;
        }
    },

    AGILITY_HARD_TRAP("Сложная ловушка на ловкость") {
        public int trapDifficulty() {
            return 15;
        }

        public int trapPerceptionThreshold() {
            return 15;
        }

        public Stat trapStat() {
            return Stat.AGILITY;
        }

        public int trapDamage() {
            return (1 + Dice.D6.roll());
        }

        @Override
        public String textTrapNotNoticed() {
            return "\nВы делаете шаг и пол под вам рушиться, оставляя на области всего несколько столпов, на которые можно было наступать. " +
                    "\nРугаясь на свою невнимательность, искусственную тьму, наведённую здесь, и вредного Саню, " +
                    "который был здесь и не триггернул ни одну ловушку, вы вылезаете из ямы. " +
                    "\nЗдесь вы находите несколько кем-то любезно оставленных досок и прокидываете их через пропасть. " +
                    "\nТак вы сможете безопасно вернуться.";
        }

        @Override
        public String textTrapNoticed() {
            return "\nВам кажется, что пол впереди вас довольно хрупкий. Просто пройти по нему не получится.";
        }

        @Override
        public String textTrapSuccess() {
            return "\nВы с силой ударяете по первому хрупкому участку и пол впереди обрушивается, оставляя стоять лишь несколько каменных столпов. " +
                    "\nДня в этой яме не видно, так что вы решаете прыгать по ним. " +
                    "\nНесколько ловких прыжков и вот вы уже на другой стороне пропасти. " +
                    "\nЗдесь вы находите несколько кем-то любезно оставленных досок и прокидываете их через пропасть. " +
                    "\nТак вы сможете безопасно вернуться.";
        }

        @Override
        public String textTrapFail() {
            return "\nВы с силой ударяете по первому хрупкому участку и пол впереди обрушивается, оставляя стоять лишь несколько каменных столпов. " +
                    "\nДна в этой яме не видно, так что вы решаете прыгать по ним. " +
                    "\nНо первый же прыжок выходит не слишком удачным и вы падаете вниз. " +
                    "\nКогда вы касаетесь головой дна ямы, тьма развеивается, и вы понимаете что яма была не глубокая. " +
                    "\nРугаясь на свою невнимательность, искусственную тьму, наведённую здесь и вредного Саню, " +
                    "который был здесь и не триггернул ни одну ловушку, вы вылезаете из ямы. " +
                    "\nЗдесь вы находите несколько кем-то любезно оставленных досок и прокидываете их через пропасть. " +
                    "\nТак вы сможете безопасно вернуться.";
        }

        public boolean reverse() {
            return false;
        }
    };

    private final String title;

    TrapType(String title) {
        this.title = title;
    }

    abstract public int trapDifficulty();
    abstract public int trapPerceptionThreshold();
    abstract public int trapDamage();
    abstract public Stat trapStat();
    abstract public String textTrapNotNoticed();
    abstract public String textTrapNoticed();
    abstract public String textTrapSuccess();
    abstract public String textTrapFail();
    abstract public boolean reverse();

}
