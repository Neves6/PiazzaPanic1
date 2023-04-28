"""UTILITY FUNCTION TO SCAN THROUGH FILE TO SEE WHAT ASSETS ARE BEING USED IN ORDER TO TEST
FOR THEIR EXISTANCE,

N.B. Any assets that cannot be detected using this method can be added to the template."""

import os

# PLEASE NOTE: Ensure output path is where gradle test directory is and make sure the output is as a .java file.
PATH_TO_SOURCE = r"C:\Users\Uni Profile\Documents\GitHub\ENG1-ASSESSMENT-TWO\core\src\com\neves6\piazzapanic"
OUTPUT_PATH = r"C:\Users\Uni Profile\Documents\GitHub\ENG1-ASSESSMENT-TWO\tests\src\com\neves6\piazzapanic\tests\assets.txt"
JAVA_TEMPLATE = r"C:\Users\Uni Profile\Documents\GitHub\ENG1-ASSESSMENT-TWO\tools\AssetTestsTemplates.java"
JAVA_OUTPUT = r"C:\Users\Uni Profile\Documents\GitHub\ENG1-ASSESSMENT-TWO\tests\src\com\neves6\piazzapanic\tests\AssetsTests.java"

file_structure = os.walk(PATH_TO_SOURCE)

def generate_string(input, to_add):
    """
    Generate a java function that tests for existance of a asset.

    :param input: Current java file as a string.
    :param to_add: Asset that needs to be added to the function.
    :return: Updated string with test for new asset.
    """
    if to_add.find(r"/") > 0:
        #Strips all punctuation for nicer file names.
        pretty_print = to_add[to_add.find(r"/") + 1:]
        pretty_print = to_add[:to_add.find(".")]
    else:
        pretty_print = to_add
    pretty_print = ''.join(x for x in pretty_print if x.isalpha())
    input += """\n 
    @Test
    public void test{0}Exists() {{
        assertTrue("'{1}' must exist for the game to compile", Gdx.files
          .internal("{2}").exists());
    }}""".format(pretty_print, to_add, to_add)

    return input

r = open(JAVA_TEMPLATE).read()

for (dirpath, dirname, filenames) in file_structure:
    assetsBeingUsed = []
    for filename in filenames:
        f = open(os.path.join(dirpath, filename))
        for line in f.readlines():
            start_val = line.find("Gdx.files.internal")
            #If the phrase existes, just clean the string to just get the path.
            if start_val > 0:
                head_removed = line[start_val + 20:]
                tail_removed = head_removed[:head_removed.find('")')]
                if tail_removed not in assetsBeingUsed:
                    assetsBeingUsed.append(tail_removed)
                    r = generate_string(r, head_removed[:head_removed.find('")')])

#Save all assets to a text file for tool debugging purposes.
file = open(OUTPUT_PATH,'w+')
for asset in assetsBeingUsed:
	file.write(asset+"\n")

#Closing } + saves java file
r += "\n }"
file = open(JAVA_OUTPUT, 'w+')
file.write(r)