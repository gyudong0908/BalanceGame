import React, { createContext, useContext, useState } from 'react';

const GameData = createContext();

export const useGameData = () => {
  return useContext(GameData);
};

export const GameProvider = ({ children }) => {
  const game = useState([]);

  return (
    <GameData.Provider value={game}>
      {children}
    </GameData.Provider>
  );
};

const UserInfo = createContext();

export const useUserInfo = () => {
  return useContext(UserInfo);
};

export const UserProvider = ({ children }) => {
  const user = useState({});

  return (
    <UserInfo.Provider value={user}>
      {children}
    </UserInfo.Provider>
  );
};