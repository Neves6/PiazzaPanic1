from datetime import date

env_file = os.getenv('GITHUB_ENV')

base_date = date(2023, 3, 2)
today_date = date.today()

delta = today_date - base_date
releaseNo = delta.days // 7 + 1


with open(env_file, "a") as myfile:
    myfile.write("STABLE_NAME={{Stable/v1.1.{}}}".format(releaseNo)))


