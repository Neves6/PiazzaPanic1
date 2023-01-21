# Generates disposals code from a list of variable declarations.
# Not all object can be disposed, so remove any flagged by your IDE.
# Example usage:
#   python .\disposalgen.py """OrthographicCamera camera;
#   >>     SpriteBatch batch;
#   >>     Animation<TextureRegion> introAnimation;
#   >>     Texture introSheet;
#   >>     BitmapFont font;
#   >>     float stateTime;
#   >>     int FRAME_COLS;
#   >>     int FRAME_ROWS;
#   >>     int winWidth;
#   >>     int winHeight;"""

import sys

print("\n".join([k.replace(";", ".dispose();") for k in [j.split(" ")[1] for j in [i.strip("\t ") for i in sys.argv[1:][0].split("\n")]]]))
