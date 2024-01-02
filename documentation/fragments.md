Fragments
=========

1. [Fragment HTML tags and attributes](#fragment-html-tags-and-attributes)
2. [Freemarker inside fragment HTML files](#freemarker-inside-fragment-html-files)
3. [JSON Schemas](#json-schemas)
4. [File references in fragment.json](#file-references-in-fragmentjson)

Fragment HTML tags and attributes
---------------------------------

When developing new fragments for Liferay you can make use of custom HTML tags or attributes
inside the HTML file. Those tags are known by the plugin, so that those tags are no longer
shown as invalid. Additionally, those tags and attributes are shown in the code completion popup.

For example the following HTML file inside a fragment collection contributor is shown as valid.

```html
<div class="row">
    <div class="col-md-12">
        <lfr-drop-zone data-lfr-drop-zone-id="1" data-lfr-priority="1"></lfr-drop-zone>
    </div>
</div>
```

The following custom tags are supported:

```html
    <lfr-drop-zone />
    <lfr-editable />
    <lfr-widget-asset-list />
    <lfr-widget-breadcrumb />
    <lfr-widget-categories-nav />
    <lfr-widget-commerce-account-portlet />
    <lfr-widget-dynamic-data-list />
    <lfr-widget-form />
    <lfr-widget-iframe />
    <lfr-widget-media-gallery />
    <lfr-widget-nav />
    <lfr-widget-related-assets />
    <lfr-widget-rss />
    <lfr-widget-site-map />
    <lfr-widget-tags-nav />
    <lfr-widget-web-content />
```

Freemarker inside fragment HTML files
-------------------------------------

It is possible to use Freemarker inside fragment HTML files. 

Code completion is available for the square bracket notation of Freemarker.

```injectedfreemarker
[#list 0..configuration.numberOfTabs-1 as i]
    <div>
        
    </div>
[/#list]
```

JSON Schemas
------------

JSON Schemas are provides for several fragment json files. By this, the files are checked if they are valid. Additionally,
code completion features are available.

JSON Schemas are available for:

* `collection.json`
* `configuration.json`
* `fragment.json`

File references in fragment.json
-------------------------------

Inside `fragment.json` you can refer to different files like `htmlFile` or `cssFile`. Those are resolved as 
file references, so that you can autocomplete available filenames. Additionally, you can rename the file and the renaming is
reflected in the json file, too.

```json
{
   "cssPath": "index.css",
   "configurationPath": "configuration.json",
   "htmlPath": "index.html",
   "jsPath": "main.js",
   "thumbnailPath": "thumbnail.png"
}
```
