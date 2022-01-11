[![Codacy Badge](https://app.codacy.com/project/badge/Grade/95df55abcc48494795a01211617c8900)](https://www.codacy.com/gh/Rekotek/censorship/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Rekotek/censorship&amp;utm_campaign=Badge_Grade)

Censorship rules the world! :)
==============================

Flexible access to the list of books that allowed to import into Ukraine
------------------------------------------------------------------------

This application makes easy to find whether some book passed cescorship. Of course, censorship is prohibited, but...

Technically, this spring-boot application periodically download file from censorship government organization, parse it
and fill internal embedded database. Employee, who fill this database, make a lot of mistakes in Publishers' name, Isbn, etc.
So you need to lookup carefully.

This project also shows you how to use multi-modules maven project, split application into modules.
