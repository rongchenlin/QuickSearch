
cd nginx-1.12.2
nginx.exe -s stop
# 关闭后台服务
@echo off
SET port=8888
for /f "usebackq tokens=1-5" %%a in (`netstat -ano ^| findstr %port%`) do (
	if [%%d] EQU [LISTENING] (
		set pid=%%e
	)
)
echo close : %port%  %pid%
if not "%pid%" == "" taskkill /f /pid %pid%
pause