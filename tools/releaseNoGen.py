from datetime import date
import os

env_file = os.getenv('GITHUB_ENV')

base_date = date(2023, 2, 23)
today_date = date.today()

delta = today_date - base_date
releaseNo = (delta.days // 7) + 1


with open(env_file, "a") as myfile:
    myfile.write("STABLE_NAME=Stable/v1.0.{}".format(releaseNo))


