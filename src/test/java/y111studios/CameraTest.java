package y111studios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import y111studios.Camera;
import y111studios.buildings.BuildingCounter;
import y111studios.buildings.BuildingType;
import y111studios.map.CollisionDetection;
import y111studios.position.GridArea;

public class CameraTest {
    public Camera camera;

  /**
   * Initializes a new instance of camera before each test, ensuring that
   * each test runs with a fresh camera.
   */
  @BeforeEach
  void setUp() {
    camera = new Camera(0, 0, 0, 0);
  }

  /**
   * Tests that the shift method moves the camera to the correct place
   * and that the camera doesnt go out of bounds
   */
  @Test
  void testShift() {
    camera.shift();
    assertEquals(0, camera.x ); // velocity of 0 case, vx
    assertEquals(0, camera.y ); // velocity of 0 case, vy
    camera.vx=10;
    camera.vy=10;
    camera.shift();
    assertEquals(22, camera.x ); // velocity of 10 case, vx
    assertEquals(22, camera.y ); // velocity of 10 case, vy
    camera.vx=-11;
    camera.vy=-11;
    camera.shift();
    assertEquals(0, camera.x ); // velocity of goes out of bounds lower case, vx
    assertEquals(0, camera.y ); // velocity of goes out of bounds lower case, vy
    camera.vx=2272;
    camera.vy=10000;
    camera.shift();
    assertEquals(5110, camera.x ); // velocity of goes out of bounds upper case, vx
    assertEquals(2905, camera.y ); // velocity of goes out of bounds upper case, vy
    camera.shift();
    assertEquals(5110, camera.x ); // velocity of goes out of bounds upper case 2x, vx
    assertEquals(2905, camera.y ); // velocity of goes out of bounds upper case 2x, vy

  }
  /**
   * Tests that the addVelocity method correctly adds
   * the vx and vy values
   */
  @Test
  void testAddVelocity() {
    camera.addVelocity(0,0);
    assertEquals(0, camera.vx ); // Case where vx is 0
    assertEquals(0, camera.vy ); // Case where vy is 0
    camera.addVelocity(10,20);
    assertEquals(10, camera.vx ); // Case where vx is positive
    assertEquals(20, camera.vy ); // Case where vy is positive
    camera.addVelocity(-20,-40);
    assertEquals(-10, camera.vx ); // Case where vx is negative
    assertEquals(-20, camera.vy ); // Case where vy is negative

  }
  /**
   * Tests that the velocityReset method correctly resets the
   * values of vx and vy to 0
   */
  @Test
  void testVelocityReset() {
    camera.velocityReset();
    assertEquals(0, camera.vx ); // Case where vx is 0
    assertEquals(0, camera.vy ); // Case where vy is 0
    camera.vx=10;
    camera.vy=10;
    camera.velocityReset();
    assertEquals(0, camera.vx ); // Case where vx is positive
    assertEquals(0, camera.vy ); // Case where vy is positive
    camera.vx=-10;
    camera.vy=-10;
    camera.velocityReset();
    assertEquals(0, camera.vx ); // Case where vx is negative
    assertEquals(0, camera.vy ); // Case where vy is negative


  }
}
