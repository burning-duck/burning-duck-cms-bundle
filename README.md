# Burning Duck Custom Magnolia Bundle


## What you need
You need the Java jdk1.8.*

You can get it from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


## Clone and start
Clone the project with git or download the zip file.

```
> git clone https://github.com/burning-duck/burning-duck-cms-bundle.git
```

Bevor you start the project you can customice the configuration.

Rename the cloned folder with you project name `[your-project-name]-cms-bundle`.

Under `[your-project-name]-cms-bundle\src\main\webapp\WEB-INF\config\local` you will find the file `magnolia.properties`.

Open the file and change the property `project.name=burning-duck-cms` to `project.name=[your-project-name]-cms`.

```
> cd [your-project-name]-cms-bundle

> mvnw jetty:run-exploded
```

After a while you should see `[INFO] Started Jetty Server` then open [http://localhost:8082](http://localhost:8082).

Now you will see the Magnolia installer page. Click `start installation` and if all task have run `start up magnolia`.

When you take a look at the folder where you cloned the project into. You will see two new folder.

The local data store from Magnolia `[your-project-name]-cms-home` and the light modules folder `[your-project-name]-cms-light`


## Usage
Now you can login with User `superuser` and PW `superuser`.

You will find all the documentation about Magnolia here [https://documentation.magnolia-cms.com/display/DOCS](https://documentation.magnolia-cms.com/display/DOCS)

To create a light module in the `[your-project-name]-cms-light` folder take a look at the burning-duck [repo](https://github.com/burning-duck) or [Light development in Magnolia](https://documentation.magnolia-cms.com/display/DOCS/Light+development+in+Magnolia)


## Questions
If you have any question about anything, feel free to create a issue, write an E-Mail or join the [Discord Chat](https://discord.gg/5KGSrfd)
