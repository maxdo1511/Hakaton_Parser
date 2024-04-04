from spacy.lang.ru import Russian
import dateparser

TIME_LINE_PREDICT_COUNTER_DELTA = 4

month = {
    "января": 1,
    "февраля": 2,
    "марта": 3,
    "апреля": 4,
    "мая": 5,
    "июня": 6,
    "июля": 7,
    "августа": 8,
    "сентября": 9,
    "октября": 10,
    "ноября": 11,
    "декабря": 12
}

timeLinePredict = ["таймлайн", "этапы", "сроки"]
registerPredict = ["регистрация", "регистрации", "регистрацией", "регистрациию", "заявка", "заявки", "заявке", "заявку"]
prezentationPredict = ["защита", "закрытие"]


class DateAnalyzeObject:
    def __init__(self, text, date):
        self.text = text
        self.date = date


class DescriptionAnalyzer:
    def __init__(self):
        pass

    def analyze(self, hakaton_info):
        global prezentationPredict
        description = hakaton_info.description
        description = description.lower()

        nlp = Russian()
        doc = nlp(description)

        dates_to_analyze = []
        timeLineIndex = -1
        prezentationPredictIndex = -1
        isTimeLineDateUpdated = False
        afterTimelinePredictCounter = 0

        for token in doc:

            # Проверка где находится таймлайн
            if token.text.lower() in timeLinePredict:
                timeLineIndex = len(dates_to_analyze)
                afterTimelinePredictCounter += 1
            # Проверка где находится регистрация
            if timeLineIndex != -1 and isTimeLineDateUpdated is False and afterTimelinePredictCounter < TIME_LINE_PREDICT_COUNTER_DELTA:
                if token.text.lower() in registerPredict:
                    timeLineIndex = len(dates_to_analyze)
                    isTimeLineDateUpdated = True
            # Проверка где находится презентация
            if timeLineIndex != -1 and prezentationPredictIndex == -1:
                if token.text.lower() in prezentationPredict:
                    prezentationPredictIndex = len(dates_to_analyze) - 1


            # Парсинг дат
            dates = dateparser.parse(token.text)
            if dates is not None:
                dates_to_analyze.append(DateAnalyzeObject(token.text, dates))
                if timeLineIndex != -1 and isTimeLineDateUpdated is False:
                    afterTimelinePredictCounter += 1

        all_normalized_dates = []
        timeLineFlag = False
        prezentationFlag = False
        for i in range(0, len(dates_to_analyze)):
            if dates_to_analyze[i].text in month:
                if i > 0:
                    if dates_to_analyze[i - 1].text.isdigit():
                        if timeLineFlag is False and timeLineIndex < i and timeLineIndex != -1:
                            timeLineIndex = len(all_normalized_dates)
                            timeLineFlag = True
                        if prezentationFlag is False and prezentationPredictIndex < i and prezentationPredictIndex != -1:
                            prezentationPredictIndex = len(all_normalized_dates) - 1
                            prezentationFlag = True
                        s_date = dates_to_analyze[i - 1].text + " " + dates_to_analyze[i].text
                        all_normalized_dates.append(dateparser.parse(s_date))

        return all_normalized_dates, timeLineIndex, prezentationPredictIndex
