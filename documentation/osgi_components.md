Editing OSGi components
=======================

Liferay 7 / DXP uses OSGi.

## Description

This plugin adds basic syntax highlighting for ```bnd.bnd``` files.

![bnd.bnd file](bnd.png "bnd.bnd file")

This plugin adds basic code completions for the ```@Component``` Annonations, so that known properties for Portlets and many other
Liferay components are provided. For several properties a quick documentation is provided (Ctrl-Q), so that
you can look up the meaning of those properties.

![Component annotation](component.png "Component annotation")

Injected component references using the ```@Reference``` annotation are marked as "unused" by IntelliJ by default.
This plugin tells IntelliJ that those are injected references, so that they are actually "written" somehow and
so they are not "unsued".

## Requirements

Should work without any special requirements
