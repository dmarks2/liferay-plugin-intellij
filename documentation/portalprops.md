portal.properties
=================

1. [Code Completion and documentation](#code-completion-and-documentation)
2. [Deprecations](#deprecations)

Code Completion and documentation
---------------------------------

For all `portal.properties` files (e.g. all files starting with `portal` and ending with `.properties`) code
completion is available. When editing such files all available properties are suggested.

![portal.properties](portalprops.png "portal.properties")

Additionally, you can look up the documentation (Quick documentation lookup, `Ctrl-Q`).

![portal.properties documentation](portalprops_documentation.png "portal.properties documentation")

*Works for Liferay 7.x, works in IntelliJ Community Edition and Ultimate Edition*

Deprecations
------------

Some properties in all `portal.properties` have been deprecated or removed between Liferay versions. The IntelliJ plugin
will detect such properties and give you a warning about it. A quick fix is available to remove the outdated property.