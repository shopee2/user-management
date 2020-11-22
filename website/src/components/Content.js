import { useState, useRef } from "react";
import { Form, Button, InputGroup, Col } from "react-bootstrap";

import empty from "../assets/empty.png";

const shopeeColor = "#FB5431";

const Content = (props) => {
  const [firstName, setFirstName] = useState(props.firstName);
  const [lastName, setLastName] = useState(props.lastName);
  const [address, setAddress] = useState(props.address);
  const [phoneNumber, setPhoneNumber] = useState(props.phoneNumber);
  const [gender, setGender] = useState(props.gender);

  const pictureRef = useRef(null);

  return (
    <div className="container-fluid ">
      <div className="row">
        <nav
          className="col-md-2 d-none d-md-block sidebar bg-light"
          style={{ height: "100vh", borderRight: "1px solid lightgrey" }}
        >
          <div className="sidebar-sticky">
            <ul className="nav flex-column">
              <li className="nav-item mt-4">
                <span className="nav-link text-dark" >
                  บัญชีส่วนตัว
                </span>
              </li>
              <li className="nav-item my-4">
                <a
                  href="/"
                  className="nav-link"
                  style={{
                    color: shopeeColor,
                  }}
                >
                  โปรไฟล์
                </a>
              </li>
            </ul>
          </div>
        </nav>

        <div className="col-md-9 ml-sm-auto col-lg-10 px-4 mt-4 pl-5">
          <h1 className="mb-5">โปรไฟล์ของฉัน</h1>

          <input
            type="file"
            hidden
            ref={pictureRef}
            onChange={props.onChangePicture}
          />
          <img
            onClick={() => pictureRef.current.click()}
            style={{
              width: "20%",
              border: "1px solid grey",
              borderRadius: 10,
              cursor: "pointer",
            }}
            className="mb-4"
            src={props.imageUrl ? props.imageUrl : empty}
            alt=""
          />

          <Form.Row>
            <Form.Group as={Col} md="3">
              <Form.Label>UID</Form.Label>
              <InputGroup>
                <Form.Control
                  type="text"
                  placeholder="UID"
                  aria-describedby="inputGroupPrepend"
                  required
                  disabled
                  value={props.uid}
                />
              </InputGroup>
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group as={Col} md="3">
              <Form.Label>ชื่อจริง</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="ชื่อจริง"
                value={firstName}
                onChange={(e) => setFirstName(e.currentTarget.value)}
              />
            </Form.Group>

            <Form.Group as={Col} md="3">
              <Form.Label>นามสกุล</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="นามสกุล"
                value={lastName}
                onChange={(e) => setLastName(e.currentTarget.value)}
              />
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group as={Col} md="3">
              <Form.Label>เพศ</Form.Label>
              <Form.Control
                as="select"
                size="md"
                custom
                value={gender}
                onChange={(e) => setGender(e.target.value)}
              >
                <option value="male">ชาย</option>
                <option value="female">หญิง</option>
                <option value="other">อื่นๆ</option>
              </Form.Control>
            </Form.Group>

            <Form.Group as={Col} md="3">
              <Form.Label>ที่อยู่</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="ที่อยู่"
                value={address}
                onChange={(e) => setAddress(e.currentTarget.value)}
              />
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group as={Col} md="3">
              <Form.Label>เบอร์ติดต่อ</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="เบอร์ติดต่อ"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.currentTarget.value)}
              />
            </Form.Group>

            <Form.Group as={Col} md="3">
              <Form.Label>วันเกิด</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="วันเกิด"
                disabled
                value={`${props.dateOfBirth.date}-${props.dateOfBirth.month}-${props.dateOfBirth.year}`}
              />
            </Form.Group>
          </Form.Row>

          <Button
            className="mt-4"
            size="lg"
            style={{
              backgroundColor: shopeeColor,
              border: "none",
            }}
            onClick={() =>
              props.onSubmit({
                firstName,
                lastName,
                address,
                phoneNumber,
                gender,
              })
            }
          >
            บันทึก
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Content;
