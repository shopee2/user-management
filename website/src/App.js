import { useState, useEffect } from "react";

import Nav from "./components/Nav";
import Content from "./components/Content";

const URL = "https://sop-picnic.azurewebsites.net";

export default function App() {
  const [isLoading, setIsLoading] = useState(true);
  const [userProfile, setUserProfile] = useState();
  const [imageUrl, setImageUrl] = useState();

  const uid = "pSFcMeuEtuY8PyVUdkYDtcCMCSi1";

  const fetchUserData = async () => {
    setIsLoading(true);
    try {
      const response = await fetch(`${URL}/profile/${uid}`);
      const data = await response.json();
      setUserProfile(data);

      const responsePicture = await fetch(`${URL}/profile/picture/${uid}`);
      const pictureData = await responsePicture.json();
      setImageUrl(pictureData.imageUrl);
    } catch (error) {
      console.log(error);
    }
    setIsLoading(false);
  };

  const submitButtonHandler = async (data) => {
    const profile = {
      ...userProfile,
      ...data,
    };
    try {
      await fetch(`${URL}/profile/edit`, {
        method: "PATCH",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(profile),
      });
      alert("บันทึกข้อมูลเรียบร้อยแล้ว");
      window.location.reload();
    } catch (error) {
      console.log(error);
    }
  };

  const changePictureHandler = async (e) => {
    const formData = new FormData();
    formData.append("file", e.target.files[0]);

    try {
      const response = await fetch(`${URL}/profile/picture/${uid}`, {
        method: "POST",
        body: formData,
      });

      const data = await response.json();

      setImageUrl(data.imageUrl);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  return (
    <div>
      <Nav uid={uid} />
      {!isLoading && (
        <Content
          imageUrl={imageUrl}
          uid={uid}
          firstName={userProfile.firstName}
          lastName={userProfile.lastName}
          address={userProfile.address}
          phoneNumber={userProfile.phoneNumber}
          gender={userProfile.gender}
          dateOfBirth={userProfile.dateOfBirth}
          onSubmit={submitButtonHandler}
          onChangePicture={changePictureHandler}
        />
      )}
    </div>
  );
}
