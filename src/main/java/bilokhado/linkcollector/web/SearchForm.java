package bilokhado.linkcollector.web;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import bilokhado.linkcollector.entity.QueryTag;
import bilokhado.linkcollector.entity.TagsList;

/**
 * A backing bean for home page.
 */
public class SearchForm implements Serializable {

	/**
	 * Version for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Search query string.
	 */
	@NotEmpty(groups = Search.class)
	@Size(max = 150)
	private String searchQuery;

	/**
	 * Text of the tag to add into the tags table.
	 */
	@NotEmpty(groups = Add.class)
	@Size(max = 50)
	private String tagText;

	/**
	 * Weight of the tag to add into the tags table.
	 */
	@NotNull(groups = Add.class)
	@Min(value = -999, groups = Add.class)
	@Max(value = 9999, groups = Add.class)
	private Integer tagWeight;

	/**
	 * Tags list.
	 */
	private TagsList tags = new TagsList();

	/**
	 * Index of currently edited tag.
	 */
	private Integer editedTagIndex;

	/**
	 * Text of edited tag.
	 */
	@NotEmpty(groups = Update.class)
	@Size(max = 50)
	private String editedTagText;

	/**
	 * Weight of edited tag.
	 */
	@NotNull(groups = Update.class)
	@Min(value = -999, groups = Update.class)
	@Max(value = 9999, groups = Update.class)
	private Integer editedTagWeight;

	/**
	 * Default no-arguments constructor.
	 */
	public SearchForm() {
	}

	/**
	 * Constructor to create form filled with data.
	 * 
	 * @param encodedSearchQuery
	 *            search query Base64 encoded string
	 * @param tagsJsonString
	 *            string representing encoded tags list
	 */
	public SearchForm(String encodedSearchQuery, String tagsJsonString) {
		this.searchQuery = new String(Base64.getUrlDecoder().decode(encodedSearchQuery), StandardCharsets.UTF_8);
		if (tagsJsonString != null && !tagsJsonString.isEmpty()) {
			try {
				tags.populateFromJson(tagsJsonString);
			} catch (Exception e) {
				// Just ignore any errors, because for searchForm filled tags
				// list is not mandatory.
				// Exception already logged by TagsList.
			}
		}
	}

	/**
	 * Adds tag to the list of tags.
	 */
	public void addTag() {
		tags.add(new QueryTag(tagText, tagWeight));
		tagWeight = null;
		tagText = null;
		if (editedTagIndex != null)
			editedTagIndex++;
	}

	/**
	 * Removes the tag from the list of tags.
	 * 
	 * @param index
	 *            the tag's index to remove from list
	 */
	public void removeTag(int index) {
		tags.remove(index);
		if (editedTagIndex != null) {
			if (index < editedTagIndex)
				editedTagIndex--;
			else if (index == editedTagIndex)
				editedTagIndex = null;
		}
	}

	/**
	 * Prepares data for tag editing.
	 * 
	 * @param index
	 *            the tag's index to edit
	 */
	public void setEditedTag(int index) {
		QueryTag editedTag = tags.getTag(index);
		editedTagIndex = index;
		editedTagText = editedTag.getTagText();
		editedTagWeight = editedTag.getTagWeight();
	}

	/**
	 * Saves tag editing result.
	 */
	public void updateEditedTag() {
		QueryTag editedTag = tags.getTag(editedTagIndex);
		editedTag.setTagText(editedTagText);
		editedTag.setTagWeight(editedTagWeight);
		editedTagIndex = null;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public String getEncodedSearchQuery() {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(searchQuery.getBytes(StandardCharsets.UTF_8));
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

	public Integer getTagWeight() {
		return tagWeight;
	}

	public void setTagWeight(Integer tagWeight) {
		this.tagWeight = tagWeight;
	}

	public TagsList getTags() {
		return tags;
	}

	public Integer getEditedTagIndex() {
		return editedTagIndex;
	}

	public String getEditedTagText() {
		return editedTagText;
	}

	public void setEditedTagText(String editedTagText) {
		this.editedTagText = editedTagText;
	}

	public Integer getEditedTagWeight() {
		return editedTagWeight;
	}

	public void setEditedTagWeight(Integer editedTagWeight) {
		this.editedTagWeight = editedTagWeight;
	}

	public interface Add {
	}

	public interface Update {
	}

	public interface Search {
	}
}
