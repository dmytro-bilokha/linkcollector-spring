package bilokhado.linkcollector.web;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 * A class representing list of tags for web pages scoring.
 */
public class TagsList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("bilokhado.linkcollector.web.TagsList");

	private final List<QueryTag> tags = new LinkedList<>();

	public List<QueryTag> getTags() {
		return tags;
	}

	public void add(QueryTag tag) {
		tags.add(0, tag);
	}

	public void append(QueryTag tag) {
		tags.add(tag);
	}

	public void remove(QueryTag tag) {
		tags.remove(tag);
	}

	public void remove(int index) {
		tags.remove(index);
	}

	public QueryTag getTag(int index) {
		return tags.get(index);
	}

	/**
	 * Normalizes tags list via removing tags with zero weight, deleting
	 * duplicates and converting tags text to lower case.
	 */
	public void normalize() {
		Map<String, Boolean> seen = new HashMap<>();
		ListIterator<QueryTag> iterator = tags.listIterator(tags.size());
		while (iterator.hasPrevious()) {
			QueryTag t = iterator.previous();
			t.setTagText(t.getTagText().toLowerCase());
			if (seen.putIfAbsent(t.getTagText(), Boolean.TRUE) != null || t.getTagWeight() == 0) {
				iterator.remove();
			}
		}
	}

	/**
	 * Calculates hash for storing in database and determining uniqueness.
	 * 
	 * @return the calculated hash
	 */
	public long calculateHash() {
		long hash = 1;
		for (QueryTag t : tags)
			hash = 31 * hash + t.hashCode();
		return hash;
	}

	/**
	 * Transforms internal query list to query tags array.
	 * 
	 * @return the array obtained from internal query list
	 */
	public QueryTag[] getTagsArray() {
		QueryTag[] array = new QueryTag[tags.size()];
		return tags.toArray(array);
	}

	/**
	 * Populates query tags list from given JSON string. All unknown tags are
	 * ignored.
	 * 
	 * @param jsonData
	 *            the string with JSON data
	 * @throws Exception
	 *             if JSON creation error occurs
	 */
	public void populateFromJson(String json64Data) throws Exception {
		String jsonData = null;
		try {
			jsonData = new String(Base64.getUrlDecoder().decode(json64Data), StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			logger.log(Level.SEVERE, "Error happen while trying to decode Base64 JSON string: " + json64Data, e);
			throw new Exception("Unable to decode tags list from URL");
		}
		try (JsonParser parser = Json.createParser(new StringReader(jsonData))) {
			String key = null;
			while (parser.hasNext()) {
				JsonParser.Event event = parser.next();
				switch (event) {
				case KEY_NAME:
					key = parser.getString();
					break;

				case VALUE_NUMBER:
					append(new QueryTag(key, parser.getInt()));
					break;

				case START_OBJECT:
				case END_OBJECT:
					break;

				default:
					logger.log(Level.SEVERE, "Unsupported tag passed in JSON string: " + jsonData);
					break;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error happen while parsing JSON string: " + jsonData, e);
			throw new Exception("Unable to decode tags list from URL");
		}
	}

	/**
	 * Converts query tags list to JSON string and returns it. In case of
	 * conversion errors, returns {@code null}.
	 */
	@Override
	public String toString() {
		StringWriter buffer = new StringWriter();
		try (JsonGenerator jgen = Json.createGenerator(buffer)) {
			jgen.writeStartObject();
			tags.forEach(t -> jgen.write(t.getTagText(), t.getTagWeight()));
			jgen.writeEnd();
			jgen.flush();
			return Base64.getUrlEncoder().withoutPadding()
					.encodeToString(buffer.toString().getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to encode list of tags", e);
			return null;
		}
	}

}
