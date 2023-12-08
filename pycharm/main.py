import pickle
from builtins import range
from typing import List
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import json
from datetime import datetime, timedelta
import pandas as pd
import pickle as pkl
import numpy as np
"""
    port 5000
    uvicorn main:app --reload --host 0.0.0.0 --port 5000
"""

# FastAPI 객체 생성
app = FastAPI()


# Request 받을 객체 생성
class Item(BaseModel):
    json: List[str]



# 포스트 형식으로 정거장 리스트 받아오기
@app.post("/predict")
async def predict(item: Item):
    """
    현재 시간을 받아서 한시간 뒤에 이용률 예측 모델
    :param item:
    :return: JSON 정거장별 이용률 리스트
    {
        "stations": [
            {"name": "station1", "usage": 12000},
            {"name": "station2", "usage": 15000},
            {"name": "station3", "usage": 18000}
            여기에 더 많은 정거장 객체를 추가할 수 있습니다...
        ]
    }
    """
    print(item.json[0])
    print(item.json[1])
    treeTempData = json.loads(item.json[0])
    treeWeatherData = json.loads(item.json[1])

    # 3일부터10일까지 최저기온
    tempDataCall = treeTempData['response']['body']['items']['item'][0]

    print(tempDataCall['taMin3'])

    # 3일부터10일까지 최저기온 데이터에서 "regId":"11C20404"인 데이터 추출
    temp_data_list = [data for data in treeTempData['response']['body']['items']['item'] if
                      data.get('regId') == '11C20404']
    print(temp_data_list)

    # 데이터프레임 생성
    temp_df = pd.DataFrame(temp_data_list,index=None)

    # 필요한 컬럼만 선택 (taMin3부터 taMin10까지)
    selected_columns1 = ['taMin3', 'taMin4', 'taMin5', 'taMin6', 'taMin7']
    
    # 인덱스 삭제
    temp_df_selected1 = temp_df[selected_columns1].reset_index(drop=True)
    print(temp_df_selected1)

    # 필요한 컬럼만 선택 (taMax3부터 taMax10까지)
    selected_columns2 = ['taMax3', 'taMax4', 'taMax5', 'taMax6', 'taMax7']
    temp_df_selected2 = temp_df[selected_columns2].reset_index(drop=True)
    print(temp_df_selected2)

    temp_df_selected1 = pd.DataFrame(temp_df_selected1)
    temp_df_selected2 = pd.DataFrame(temp_df_selected2)

    # 두 개의 데이터프레임을 튜플로 합치기
    combined_tuples = list(zip(temp_df_selected1.values.flatten(), temp_df_selected2.values.flatten()))

    # 새로운 데이터프레임 생성
    combined_df = pd.DataFrame(combined_tuples, columns=['최저기온(°C)', '최고기온(°C)'])

    
    # 3일부터 7일까지의 날짜 계산
    dates = [datetime.now() + timedelta(days=i) for i in range(3, 8)]
    formatted_dates = [date.strftime("%m-%d %H:00:00") for date in dates]

    # 날짜를 열로 추가
    combined_df['날짜'] = formatted_dates
    # 결과 출력
    print(combined_df)

# ================================================================================================================== #
    # 강수 확률
    # 강수 확률 데이터 전처리
    weatherDataCall = treeWeatherData['response']['body']['items']['item'][0]

    print(weatherDataCall)
    # 3일부터10일까지 강수확률 데이터에서 "regId":"11C20000"인 데이터 추출
    weather_data_list = [data1 for data1 in treeWeatherData['response']['body']['items']['item'] if
                         data1.get('regId') == '11C20000']

    print(weather_data_list)
    # 컬럼 데이터 담아두기
    num_list = [weather_data_list[0]['rnSt3Am'],
                weather_data_list[0]['rnSt3Am'],
                weather_data_list[0]['rnSt4Am'],
                weather_data_list[0]['rnSt4Am'],
                weather_data_list[0]['rnSt5Am'],
                weather_data_list[0]['rnSt5Am'],
                weather_data_list[0]['rnSt6Am'],
                weather_data_list[0]['rnSt6Am'],
                weather_data_list[0]['rnSt7Am'],
                weather_data_list[0]['rnSt7Am']]
    print(num_list)

    # for문 사용하여 강수유무 판별 데이터 담기
    rain_result = []
    cnt = 0;
    for i in range(len(num_list)):
        if (i % 2 == 1):
            if (num_list[i - 1] >= 50 or num_list[i] >= 50):
                rain_result.append(1)
            else:
                rain_result.append(0)
    print(rain_result)

    # '강수유무' 컬럼 추가
    combined_df['강수유무'] = rain_result

    dates = [datetime.now() + timedelta(days=i) for i in range(3, 8)]
    formatted_dates2 = [date.strftime("%m-%d") for date in dates]

    # 날짜를 열로 추가 > 주간/주말 컬럼 생성, 날짜 데이터 전처리
    combined_df['날짜'] = formatted_dates2
    # '날짜' 열을 datetime 형식으로 변경
    combined_df['날짜'] = formatted_dates2
    # '날짜' 열을 날짜 형식으로 변경
    combined_df['날짜'] = pd.to_datetime('2023-' + combined_df['날짜'], format='%Y-%m-%d')

    # '요일' 열 추가 (0: 월요일, 1: 화요일, ..., 6: 일요일)
    combined_df['요일'] = combined_df['날짜'].dt.dayofweek

    # '주간/주말' 열 추가 (1: 주간(월-금), 0: 주말(토-일))
    combined_df['주간/주말'] = combined_df['요일'].map(lambda x: 1 if x < 5 else 0)

    # '요일' 열 삭제
    combined_df.drop('요일', axis=1, inplace=True)
    print(combined_df)
    # '날짜' 열을 'MMDD' 형식으로 변경
    combined_df['날짜'] = pd.to_datetime(combined_df['날짜']).dt.strftime('%m%d')
    # '날짜' 열을 int64로 변환
    combined_df['날짜'] = combined_df['날짜'].astype(int)
    print(combined_df.info())

    print(combined_df)


    # 위도 경도 데이터
    station = pd.read_csv('대여소 위도,경도.csv', usecols=lambda x: x not in ['Unnamed: 0'])
    print(station)

    # 결과 출력
    print(combined_df)
    combined_df = combined_df[['날짜','최저기온(°C)','최고기온(°C)','강수유무','주간/주말']]
    final_station=[]
    # 일별 총 대여소 날씨 데이터 만들기
    for i in range(3, 8):

        WP = combined_df.iloc[:i]
        # 복제할 횟수
        num_rows = 615
        # 데이터프레임 복제하여 새로운 데이터프레임 생성
        new_WP = pd.concat([WP] * num_rows, ignore_index=True)
        new_station = pd.concat([station] * 5, ignore_index=True)
        final_station = pd.concat([new_station, new_WP], axis=1)

        final_station = final_station[['날짜', 'use_start_lng', 'use_start_lat', '최저기온(°C)', '최고기온(°C)', '강수유무', '주간/주말']]
    # 결과 확인

    final_station['날짜'] = final_station['날짜'].astype('int64')
    final_station['최저기온(°C)'] = final_station['최저기온(°C)'].astype('float64')
    final_station['최고기온(°C)'] = final_station['최고기온(°C)'].astype('float64')
    print(final_station)
    print(final_station.info())
    final_station = final_station.sort_values(by='날짜')
    #
    return_station = final_station.rename(columns={'use_start_lng': 'use_end_lng', 'use_start_lat': 'use_end_lat'})
    print(return_station)
    return_station = return_station.sort_values(by='날짜')

    # # 3일부터 10일까지의 날짜 계산
    # dates = [datetime.now() + timedelta(days=i) for i in range(3, 11)]
    # formatted_dates = [date.strftime("%m-%d %H:00:00") for date in dates]
    #
    #
    # 대여 예측모델 파일 불러오기
    print("모델 불러오기")
    use_model = pickle.load(open('xgb_대여모델2.pkl', 'rb'))

    print(type(use_model))

    print("예측하기")

    use_predict = use_model.predict(final_station)
    print(use_predict)

    # # 반납 예측모델 파일 불러오기
    return_model = pickle.load(open("xgb_반납모델2.pkl", "rb"))
    return_predict = return_model.predict(return_station)
    print(return_predict)

    # 예측값 배열 (가정)
    use_predictions = use_predict  # 총 3075개의 예측값

    # 배열을 2D 배열로 변환 (615개씩 5개의 열로)
    use_predictions_2d = use_predictions.reshape(-1, 615)

    # 각 행은 하루의 데이터를 나타냄 / 대여자 총합
    for i, daily_predictions in enumerate(use_predictions_2d, start=1):
        print(f"{i+2}일차 대여자 총합 :", np.sum(daily_predictions))

    # 예측값 배열 (가정)
    return_predictions = return_predict  # 총 3075개의 예측값

    # 배열을 2D 배열로 변환 (615개씩 5개의 열로)
    return_predictions_2d = return_predictions.reshape(-1, 615)
    # 반납자 총합
    for i, daily_predictions in enumerate(return_predictions_2d, start=1):
        print(f"{i+2}일차 반납자 총합 :", np.sum(daily_predictions))
    try:
        # 머신러닝 모델을 넣는 공간입니다.

        response = {"3일차 대여자 총합": 5517.143}
        return response

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
