# How this tool came into existence
Remember ResourceBundler in Eclipse ? Are you frustrated by the fact that you have to open
`SpringToolSuite` or `eclipse` just to sort your properties file?
with this tool, you no longer have to do that!

# How to use
just run `make` if you have it
or run `gradle run`

remember to change the `messages.properties` path in resources `application.properties` before running

# How to insert new lines?
just add your new lines in `add.txt` in original format, program will convert that into unicode code points and add them in(of course, it will sort the file later)
