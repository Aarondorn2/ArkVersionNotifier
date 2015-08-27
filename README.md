Ark Version Notifier
=============================

This project is designed to notify subscribers when an update is available for the game 'Ark: Survival Evolved'.  It is written in Java and designed to run on Google App Engine.  It has a UI to allow users to subscribe or unsubscribe from various email options including when a new version is announced, the ETA changes, and/or the version is released.  The app periodically polls an API provided by http://Ark.Bar to get information regarding the version / upcoming version of the game.

I've been doing Java for a while, but this is my first time working with many things in this project, including: Google App Engine, App Engine Datastore (or any NoSQL-type datastore), Maven, GitHub, and Quartz (job scheduling for executing code at set intervals).


Update (8/16/15): Quartz won't work with GAE!  Guess I'm learning cron tomorrow. :|

Update (8/24/15): Kron was easy - took about 20 min to implement - but very unflexable (can't alter the schedule programatically).  Everything seems good to go, but I'm working on getting a bigger email quota before I release.

Update (8/27/15): GAE finally increased my mail quota.  I've released the app on the wild!