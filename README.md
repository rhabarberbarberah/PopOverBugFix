# PopOverBugFix
This repo holds a short example to test the ControlsFX-PopOver-Bug described in [rhabarberbarberah:HotfixPopOverOnWindowClose](https://github.com/rhabarberbarberah/controlsfx/tree/HotfixPopOverOnWindowClose)

## Explanation
When starting the application, you'll be prompted to decide, if you want to start with or without the fix.

Afterwards you'll be shown a minimal UI that allows you to show PopOvers, by hovering the button.
Depending on your initial descision the application will exit with an exception, in case you close the window using the "x"-button, while the PopOvers are visible.
