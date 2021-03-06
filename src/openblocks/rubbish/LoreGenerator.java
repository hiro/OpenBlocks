package openblocks.rubbish;

import static openmods.words.Words.*;

import java.util.Map;
import java.util.Random;

import openmods.words.IGenerator;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class LoreGenerator {

	private static final IGenerator heroGenerator = createHeroGenerator();
	private static final IGenerator loreGenerator = createLoreGenerator();

	private static final Random random = new Random();

	public static String generateLore(String playerName, String itemName) {
		Map<String, String> params = Maps.newHashMap();
		params.put("player", playerName);
		params.put("item", itemName);
		return generate(loreGenerator, params);
	}

	protected static String generate(IGenerator generator, Map<String, String> params) {
		return StringUtils.capitalize(generator.generate(random, params)).replaceAll("\\s+", " ");
	}

	private static IGenerator createHeroGenerator() {
		IGenerator heroesPrefix = alt("Grunnar", "Hermann", "Sven", "Grarg", "Blarf", "Zerg", "Hans", "Nathan", "Oglaf", "Eric", "Bob");
		IGenerator heroesPostfix = alt("", "ish", "ilde", "monkeybutt", "son", "shvili", "berg", "us");
		IGenerator heroName = word(heroesPrefix, heroesPostfix);
		IGenerator heroOptional = alt("slightly", "sometimes", "mistakenly", "somehow");
		IGenerator heroAdj = alt("insane", "brave", "smelly", "philosophical", "jumping", "toothless", "burning", "heroic", "shy", "narcoleptic", "manly", "girly", "non-euclidian");
		IGenerator heroTitle = alt("babycrusher", "wrestler", "nitpicker", "barber", "anesthesiologist", "sharpshooter", "blorg", "insurance salesman", "rouge (lvl. 9)");
		IGenerator classicHeroes = capitalizeFully(seq(heroName, "the", seq(opt(0.2f, heroOptional), heroAdj, heroTitle)));

		IGenerator firstName = alt("Bill", "Juliet", "Nigel", "Steve", "Parsnip", "Cucumber", "Ludwig", "Markus", "Sven", "Clark", "Carl", "Throatwobbler", "Raymond");
		IGenerator lastNameComponent = alt("Smith", "Weston", "Banana", "Drum", "Forklift", "Ampersand", "Fruitbat", "Fhtagn", "Svenson", "Stein", "Gutenabend", "Mangrove");
		IGenerator lastName = alt(lastNameComponent, word(lastNameComponent, "-", lastNameComponent));
		IGenerator pseudonym = alt("Duckie", "Nosepicker", "Snort", "Bomber", "Ouch", "Anvil", "Halfslab", "Radiator", "Barbie");
		IGenerator namePrefix = alt("Dr.", "Rev", "Ms", "Mr", "Prof.", "Hon.", "Sgt.", "Cmdr.", "Sir", "Lady");
		IGenerator middleName = alt("W.", "T.", "F.");
		IGenerator nameSuffix = alt("M.Sc", "Ph.D", "OBE", "Jr.", "Sr.", "III", "II", "St.");

		IGenerator modernHeroes = seq(opt(0.2f, namePrefix),
				firstName,
				opt(0.1f, middleName),
				opt(0.4f, word("\"", pseudonym, "\"")),
				lastName,
				opt(0.2f, nameSuffix));

		return alt(classicHeroes, modernHeroes);
	}

	private static IGenerator createLoreGenerator() {
		IGenerator adj1 = alt("overpowered", "misspelled", "store-brand", "unsettling", "unremarkable", "sleazy", "boring", "golden", "junky");
		IGenerator adj2 = alt("cursed", "legendary", "unique", "penultimate", "awesome", "suboptimal", "mighty", "ridiculously", "slightly");
		IGenerator adjs = seq(opt(0.7f, adj2), adj1);
		IGenerator parts = alt("codpiece", "loincloth", "tootbrush", "dental floss", "eggbeater", "rubber chicken with a pulley in the middle", "shovel", "hammoc", "panties", "spatula");

		IGenerator placeAdj = alt("deadly", "dreadful", "boring", "cheap", "backwater");
		IGenerator kingdomAdjective = alt("loathing", "meat", "potatoes", "hydrocarbonates", "sweden", "slighlty unpleasant things", "herpaderp");
		IGenerator kingdomish = seq(opt(0.3f, placeAdj), alt("kingdom", "cave", "gorge", "convention", "pit", "bazaar"));
		IGenerator placeWithAdj = seq(kingdomish, "of", kingdomAdjective, opt(0.2f, seq("and", kingdomAdjective)));
		IGenerator mountainName = alt("lard", "butter", "rotten eggs", "brimstone", "newts", "doom", "croc", "flipflop");
		IGenerator mountain = seq("Mt.", opt(0.4f, placeAdj), mountainName);
		IGenerator places = capitalizeFully(alt(placeWithAdj, mountain, "dalania", "prussia", "foobaria"));

		IGenerator otherPeople = alt("youtube personalities", "dwarves", "villagers", "elves", "tax collectors", "quality testers", "boring people");
		IGenerator actor = seq(alt(heroGenerator, seq(otherPeople, "of", places)));

		IGenerator story = alt("that nobody cares about", seq("that previously belonged to", actor));
		IGenerator epicLoot = seq(opt(0.5f, adjs), parts, story);

		IGenerator created = seq(alt("repurposed from", "originally bundled with ", "forged from", "not to be mistaken with"), epicLoot);
		IGenerator loaned = seq("loaned to", actor);
		IGenerator forgotten = seq("forgotten in", alt("post office", "loo", "deep hole", "hurry"));
		IGenerator origin = alt(created, seq(alt("stolen", loaned, "imagined", forgotten, "found behind couch"), "by", heroGenerator));

		IGenerator itemModifier = alt("replica of");
		IGenerator itemAction = alt("beating", "bleeding", "winds", "things", word(sub("item"), "ing"), "cooking", "looting", "scrubing", "backpain", "hernia");
		IGenerator itemType = alt(sub("item"), alt("gizmo", "thingmajig", "doodad", "tat", "thingie"));
		IGenerator item = capitalize(seq(opt(0.9f, adjs), itemType, opt(0.9f, seq("of", itemAction))));
		IGenerator fullItem = seq(opt(0.1f, itemModifier), item);

		IGenerator taunt = alt("wimp", "noob", "git", "fool");
		IGenerator playerGet = seq(alt("stolen from", "found in", "bought in", "dug out in", "smuggled from"), places);
		IGenerator ownerInfo = seq(playerGet, "by", opt(0.3f, seq(taunt, "named")), sub("player"));

		IGenerator randomItems = alt("bananas", "grapes", "hairpins", "corks", "shuffling", "squash", "penguins");
		IGenerator universitySpeciality = alt(randomItems, seq(randomItems, "and", randomItems));
		IGenerator universityLocation = alt("woolloomooloo", "breslau", "uzbekistan", "north korea", "lower intestine");
		IGenerator university = capitalizeFully(seq("university of", alt(universityLocation, universitySpeciality)));

		IGenerator foundationFirst = alt("lick", "pick", "poke", "prod", "smell", "ring", "steal", "hug", "kick", "fwap");
		IGenerator foundationSecond = alt("fish", "sauce", "leopard", "pick", "smell", "mayonaise", "steal", "grave", "derp");
		IGenerator foundation = seq(word(capitalizeFully(foundationFirst), "-a-", capitalizeFully(foundationSecond)), "foundation");

		IGenerator organization = alt(university, foundation);

		IGenerator restoredInfo = seq("restored by", organization);
		IGenerator recent = seq("Recently", alt(ownerInfo, restoredInfo));

		IGenerator extra = alt("$1.99 each", "5 quids in plain wrapper", "Accept no substitues", "Made in China", "Batteries not included");
		return word(fullItem, opt(0.5f, seq(",", origin)), opt(0.5f, seq(".", recent)), opt(0.2f, seq(".", extra)));
	}

	public static void main(String[] argv) {
		// Left for fun!
		System.out.println("Combination count: " + loreGenerator.count().doubleValue());

		for (int i = 0; i < 50; i++) {
			System.out.println(generateLore("xxx", "yyy"));
		}

		for (int i = 0; i < 50; i++) {
			System.out.println(generate(heroGenerator, Maps.<String, String> newHashMap()));
		}
	}
}
