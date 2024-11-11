ST-Scenes for Minecraft 1.20 - Latest branch
============================================

## Mod Description

ST-Scenes is a mod built from the ground up and used to create custom scenes, custom NPCs, and more. Suitable for map makers,
creators of assemblies with mods, and just screenwriters at heart. In total, the mod adds an NPC creator and a scene customizer.

Currently in early beta.

## FAQ:
1)How to add your own NPC texture?
-To add your own NPC texture follow the steps below:

  * 1.Open ST-Scenes mod with any archiver

  * 2.Go to "assets/stscenes/textures/entity/player/"
and there either create your own folder and upload your texture file to it, or upload your texture to the slim/wide folder.

  * 3.Save the files and run minecraft

2)What is a scene, triggers, action?

-Wow, this is going to take a while, in a nutshell:

A scene is a program that is run either manually through a scene setting, or through a trigger,
which depending on its type reacts to something and starts the scene. Scene contains Actions,
they are divided into complex and simple.Complex actions are needed to tell the NPC to go to some point,
say something, teleport somewhere, etc.Simple actions are needed to for example execute a command,
turn on another scene, etc. When a scene is running, Actions are switched sequentially from top to bottom.
Note actions like "Go to", "Follow" assign the state of the NPC, and this means that when you switch such an action NPC
does not stop going or following someone, and it stops either if you disable this state by another action,
or at the end of the scene.

## Links:
[Downloads on CurseForge](https://legacy.curseforge.com/minecraft/mc-mods/st-scenes)   
[Downloads on MinecraftInside](https://minecraft-inside.ru/mods/171244-st-scenes.html)

[Official tutorial](https://youtu.be/vRN5HDVRZwI)
