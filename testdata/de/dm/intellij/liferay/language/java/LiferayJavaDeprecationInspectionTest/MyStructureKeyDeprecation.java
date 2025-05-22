import com.liferay.journal.model.JournalArticle;

public class MyStructureKeyDeprecation {

	public static void main(String[] args) {
		JournalArticle journalArticle = new JournalArticle();

		<warning descr="For JournalArticle, ddmStructureKey support has been removed. Use ddmStructureId instead. (see LPS-178619)">journalArticle.<error descr="Cannot resolve method 'setDDMStructureKey' in 'JournalArticle'">setDDMStructureKey</error>("FOO")</warning>;
	}
}
