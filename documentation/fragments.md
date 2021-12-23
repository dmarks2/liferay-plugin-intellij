Fragments
=========

1. [Fragment HTML tags and attributes](#fragment-html-tags-and-attributes)

Fragment HTML tags and attributes
---------------------------------

When developing new fragments for Liferay you can make use of custom HTML tags or attributes
inside the HTML file. Those tags are known by the plugin, so that those tags are no longer
shown as invalid. Additionally those tags and attributes are shown in the code completion popup.

For example the following HTML file inside a fragment collection contributor is shown as valid.

```html
<div class="row">
    <div class="col-md-12">
        <lfr-drop-zone data-lfr-drop-zone-id="1" data-lfr-priority="1"></lfr-drop-zone>
    </div>
</div>
```