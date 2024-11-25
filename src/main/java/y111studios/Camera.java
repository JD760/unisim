package y111studios;

/**
 * Stores the camera position, velocity and scale.
 */
public class Camera {

    /**
     * The camera's x coordinate.
     */
    int x;
    /**
     * The camera's y coordinate.
     */
    int y;
    /**
     * The camera's horizontal velocity.
     */
    int width;
    int height;
    int vx;
    /**
     * The camera's vertical velocity.
     */
    int vy;
    /**
     * The camera's scale.
     */
    float scale;

    /**
     * Initializes the camera at the given coordinates.
     */
    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        vx = 0;
        vy = 0;
        scale = 2.25f;
    }

    /**
     * Updates camera position based on velocity.
     */
    public void shift() {
        x += (int) (vx * scale);
        if(x > 5110 - width * scale) x = 5110 - (int)(width * scale);
        if(x < 0) x = 0;
        y += (int) (vy * scale);
        if(y > 2680 - (height - 100) * scale) y = 2680 - (int)((height - 100) * scale);
        if(y < 0) y = 0;
    }

    /**
     * Changes the velocity of the camera.
     * 
     * @param vx The change to horizontal velocity.
     * @param vy The change to vertical velocity.
     */
    public void addVelocity(int vx, int vy) {
        this.vx += vx;
        this.vy += vy;
    }

    /**
     * Resets the camera's velocity
     * 
     */
    public void velocityReset() {
            this.vx = 0;
            this.vy = 0;
    }

}
