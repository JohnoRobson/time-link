# time-link
Make sure that the datasource in your Wildfly installation's JNDI is: java:/jboss/datasources/MySQL57

# How to import the project
1. Use git to put the time-link directory into your workspace as usual.
2. In Eclipse, use the "File" > "Import..." command.
3. In the dialog that opens, choose "Other" > "JSF Project" and click "Next."
4. Then in the Next window, click on the "Browse" button and navigate to where the web.xml file is located in the project.  It should be in "time-link\WebContent\WEB-INF\web.xml"  Select it and click "Next."
5. On the next screen, choose the correct servlet version (3.1) and chose your runtime and server to use.  Then click Finish.
6. Your project is now imported

There is a sql script in the /resources folder that is the same as the one on the google drive, but it also has important sample data in it such as labour grades and employees.  I suggest using it.


UI - the UI has been templated. so if you make new xhtml pages, make sure that you are using the templates. See index.xhtml for example.