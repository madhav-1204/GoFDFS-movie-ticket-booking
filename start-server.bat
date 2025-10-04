@echo off
title GoFDFS Movie Booking Server
cd /d "%~dp0"
echo.
echo ====================================
echo   Starting GoFDFS Server...
echo ====================================
echo.
java -jar target\movie-ticket-booking-1.0-SNAPSHOT-jar-with-dependencies.jar
echo.
echo Server stopped.
pause
