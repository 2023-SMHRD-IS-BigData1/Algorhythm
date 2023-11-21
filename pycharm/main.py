from datetime import datetime
from typing import List
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

"""
    port 5000
    스프링 부트로부터 정거장 이름을 받아
    모델로 한 시간 뒤의 이용률을 예측하여 반환
    반환 값은 JSON이며, 정거장 별로 이용률을 List로
"""

# FastAPI 객체 생성
app = FastAPI()

# Request 받을 객체 생성
class Item(BaseModel):
    stations: List[str]

# 포스트 형식으로 정거장 리스트 받아오기
@app.post("/predict")
async def predict(item : Item) :
    """
    현재 시간을 받아서 한시간 뒤에 이용률 예측 모델
    :param item:
    :return: JSON 정거장별 이용률 리스트
    {
        "stations": [
            {"name": "station1", "usage": 12000},
            {"name": "station2", "usage": 15000},
            {"name": "station3", "usage": 18000}
            // 여기에 더 많은 정거장 객체를 추가할 수 있습니다...
        ]
    }
    """
    try:
        # 머신러닝 모델을 넣는 공간입니다.

        response = {"data":"test"}
        return response

    except Exception as e :
        raise HTTPException(status_code=500, detail=str(e))