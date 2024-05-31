package galena.oreganized;

import galena.oreganized.index.OEffects;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec.ConfigValue;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.apache.commons.lang3.tuple.Pair;

@SuppressWarnings("UnstableApiUsage")
public class OreganizedConfig {
    public static final Common COMMON;
    public static final Client CLIENT;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;

    public static class Common {
        public final ConfigValue<Boolean> stunningOrPoison;
        public final ConfigValue<Boolean> leadPoisining;
        public final ConfigValue<Integer> leadClusterSize;
        public final ConfigValue<Integer> leadFrequency;
        public final ConfigValue<Integer> leadMinHeight;
        public final ConfigValue<Integer> leadMaxHeight;

        public final ConfigValue<Integer> silverClusterSize;
        public final ConfigValue<Integer> silverFrequency;
        public final ConfigValue<Integer> silverMinHeight;
        public final ConfigValue<Integer> silverMaxHeight;
        public final ConfigValue<Float> silverHidden;

        public final ConfigValue<Boolean> ravagerSilver;

        private Common(ModConfigSpec.Builder builder) {
            builder.comment("Common");
            builder.push("common");
            stunningOrPoison = builder.comment("Should lead poisoning events give the Stunning effect or just Poison").define("leadPoisoningStunning", true);
            leadPoisining = builder.comment("Should eating an item give lead poisoning when a lead item is in the hotbar").define("leadPoisoning", true);
            ravagerSilver = builder.comment("Ravagers have a chance of dropping Silver Nuggets upon death").define("ravagerSilver", false);
            builder.comment("Ore Generation");
            builder.push("oreGen");
            builder.comment("Lead");
            builder.push("lead");
            leadClusterSize = builder.comment("The average cluster size lead ore generates in").define("leadClusterSize", 13);
            leadFrequency = builder.comment("How frequent lead ore generates").define("leadFrequency", 10);
            leadMinHeight = builder.comment("Minimum y level that lead ore can generate").define("leadMinHeight", -40);
            leadMaxHeight = builder.comment("Maximum y level that lead ore can generate").define("leadMaxHeight", -20);
            builder.pop();
            builder.comment("Silver");
            builder.push("silver");
            silverClusterSize = builder.comment("The average cluster size silver ore generates in").define("silverClusterSize", 3);
            silverFrequency = builder.comment("How frequent silver ore generates").define("silverFrequency", 2);
            silverMinHeight = builder.comment("Minimum y level that silver ore can generate").define("silverMinHeight", -15);
            silverMaxHeight = builder.comment("Maximum y level that silver ore can generate").define("silverMaxHeight", 5);
            silverHidden = builder.comment("How often should Silver Ore generate exposed to air?").define("silverHidden", 0.2F);
            builder.pop();
        }
    }

    public static class Client {

        public Client(ModConfigSpec.Builder builder) {
            builder.comment("Client");
            builder.push("client");
            builder.pop();
        }
    }

    static {
        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        final Pair<Client, ModConfigSpec> cleintSpecPair = new ModConfigSpec.Builder().configure(Client::new);

        COMMON = commonSpecPair.getLeft();
        CLIENT = cleintSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        CLIENT_SPEC = cleintSpecPair.getRight();
    }

    public static MobEffect StunningOrPoison() {
        return COMMON.stunningOrPoison.get() ? OEffects.STUNNING.get() : MobEffects.POISON;
    }

    public static boolean stunningFromConfig() {
        return COMMON.stunningOrPoison.get();
    }
}
