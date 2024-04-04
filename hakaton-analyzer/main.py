# -*- coding: utf-8 -*-

from DescriptionAnalyzer import DescriptionAnalyzer
from data.HakatonInfo import HakatonInfo

data = []
with open("hakaton-analyzer/data.txt", "r", encoding="utf-8") as f:
    data = [line for line in f.read().split("***/***")]

analyzer = DescriptionAnalyzer()
counter = 0
hakaton_infos = []
for item in data:
    hakaton_info = HakatonInfo(
        "title " + str(counter),
        "linkSite",
        "linkHakaton",
        item,
        "dateStart",
        "dateEnd",
        "dateRegisterStart",
        "dateRegisterEnd"
    )
    dates, timeLineIndex, prezentationPredictIndex = analyzer.analyze(hakaton_info)
    hakaton_info.dateRegisterEnd = dates[len(dates) - 1]
    if timeLineIndex != -1 and timeLineIndex < len(dates):
        hakaton_info.dateStart = dates[timeLineIndex]
    if prezentationPredictIndex != -1 and prezentationPredictIndex < len(dates):
        hakaton_info.dateEnd = dates[prezentationPredictIndex]
    hakaton_infos.append(hakaton_info)
    counter += 1

for hakaton_info in hakaton_infos:
    print(
        hakaton_info.title,
        "Дата начала: " + str(hakaton_info.dateStart),
        "Дата окончания: " + str(hakaton_info.dateEnd),
        "Дата регистрации: " + str(hakaton_info.dateRegisterEnd)
    )