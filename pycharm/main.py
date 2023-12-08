from builtins import range
from typing import List
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import json
from datetime import datetime, timedelta
import pandas as pd
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
    selected_columns1 = ['taMin3', 'taMin4', 'taMin5', 'taMin6', 'taMin7', 'taMin8', 'taMin9', 'taMin10']
    
    # 인덱스 삭제
    temp_df_selected1 = temp_df[selected_columns1].reset_index(drop=True)
    print(temp_df_selected1)

    # 필요한 컬럼만 선택 (taMax3부터 taMax10까지)
    selected_columns2 = ['taMax3', 'taMax4', 'taMax5', 'taMax6', 'taMax7', 'taMax8', 'taMax9', 'taMax10']
    temp_df_selected2 = temp_df[selected_columns2].reset_index(drop=True)
    print(temp_df_selected2)

    temp_df_selected1 = pd.DataFrame(temp_df_selected1)
    temp_df_selected2 = pd.DataFrame(temp_df_selected2)

    # 두 개의 데이터프레임을 튜플로 합치기
    combined_tuples = list(zip(temp_df_selected1.values.flatten(), temp_df_selected2.values.flatten()))

    # 새로운 데이터프레임 생성
    combined_df = pd.DataFrame(combined_tuples, columns=['최저기온(°C)', '최고기온(°C)'])

    
    # 3일부터 10일까지의 날짜 계산
    dates = [datetime.now() + timedelta(days=i) for i in range(3, 11)]
    formatted_dates = [date.strftime("%m-%d %H:00:00") for date in dates]

    # 날짜를 열로 추가
    combined_df['날짜'] = formatted_dates
    # 결과 출력
    print(combined_df)


    # # 강수 확률 데이터 전처리
    # weatherDataCall = treeWeatherData['response']['body']['items']['item'][0]
    #
    # print(weatherDataCall)
    # # 3일부터10일까지 강수확률 데이터에서 "regId":"11C20000"인 데이터 추출
    # weather_data_list = [data1 for data1 in treeWeatherData['response']['body']['items']['item'] if
    #                      data1.get('regId') == '11C20000']
    #
    # print(weather_data_list)
    #
    # # 데이터프레임 생성
    # weather_df = pd.DataFrame(weather_data_list, index=None)
    #
    # selected_columns3 = ['rnSt3Am', 'rnSt3Pm', 'rnSt4Am', 'rnSt4Pm', 'rnSt5Am', 'rnSt5Pm', 'rnSt6Am', 'rnSt6Pm',
    #                      'rnSt7Am', 'rnSt7Pm', 'rnSt8', 'rnSt9', 'rnSt10']
    #
    #
    # # 인덱스 삭제
    # weather_df_selected3 = weather_df[selected_columns3].reset_index(drop=True)
    #
    # weather_df_selected4 = pd.DataFrame(weather_df_selected3)
    #
    # # 결과 출력
    # print(weather_df_selected4)
    # print(weather_df_selected4.shape)
    # for i in range(3, 8):
    #     am_col = f'rnSt{i}Am'
    #     pm_col = f'rnSt{i}Pm'
    #     new_col = f'rnSt{i}'
    #     # 해당 일자에 대한 조건식을 작성하여 새로운 컬럼 추가
    #     weather_df_selected4[new_col] = weather_df_selected4.where((weather_df_selected4[am_col] >= 50) | (weather_df_selected4[pm_col] >= 50))
        # weather_df_selected4.drop(am_col, axis=1, inplace=True)
        # weather_df_selected4.drop(pm_col, axis=1, inplace=True)

    # for i in range(8, 11):
    #     new_col = f'rnSt{i}'
    #     weather_df_selected3[new_col] = weather_df_selected3.where((weather_df_selected3[new_col] >= 50), 1, 0)
    # weather_df_selected3 = weather_df_selected3[['rnSt3', 'rnSt4', 'rnSt5', 'rnSt6', 'rnSt7', 'rnSt8','rnSt9', 'rnSt10']].T
    # weather_df_selected3['강수유무'] = weather_df_selected3
    # weather_df_selected3.drop(0, axis=1, inplace=True)
    # print(weather_df_selected3)
    # # '강수량' 컬럼 생성
    # rnst_cols_am_pm = [f'rnSt{i}Am' for i in range(3, 11)] + [f'rnSt{i}' for i in range(8, 11)]
    # weather_df_selected3['강수량'] = weather_df_selected3[rnst_cols_am_pm].apply(tuple, axis=1)
    #
    # # 결과 출력
    # print(weather_df_selected3)
    # # 두 개의 데이터프레임을 튜플로 합치기
    # combined_tuples = list(zip(temp_df_selected1.values.flatten(), temp_df_selected2.values.flatten()))
    #
    # # 새로운 데이터프레임 생성
    # combined_df = pd.DataFrame(combined_tuples, columns=['최저기온(°C)', '최고기온(°C)'])

    # 3일부터 10일까지의 날짜 계산
    dates = [datetime.now() + timedelta(days=i) for i in range(3, 11)]
    formatted_dates = [date.strftime("%m-%d %H:00:00") for date in dates]





    try:
        # 머신러닝 모델을 넣는 공간입니다.

        response = {"data": "test"}
        return response

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
