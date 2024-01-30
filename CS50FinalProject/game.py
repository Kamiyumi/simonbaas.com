import pygame
import sys
import math
import random


debug_color = (255, 0, 0)  # Red color


########################################################################
def main_game(screen):  # Initialize Pygame
    pygame.init()

    level = 1  # Starting level
    level_up_timer = pygame.time.get_ticks()  # Timer to track level progression
    level_up_interval = 1000  # 15 seconds for each level

    quack_path = "CS50FinalProject/media/quack.mp3"
    quack_effect = pygame.mixer.Sound(quack_path)
    font_path = "CS50FinalProject\media\erainyhearts.ttf"
    font_size = 48
    font = pygame.font.Font(
        font_path, font_size
    )  # This will use the default Pygame font. If you want a pixel font, you need to provide the path to a .ttf file
    # Screen dimensions
    screenwidth = 1600
    screenheight = 800

    # Add these lines to your initialization section

    beetle_start_level = 4
    butterfly_start_level = 7

    # Initialize timers for delay tracking
    beetle_reset_timer = None
    butterfly_reset_timer = None
    heart_reset_timer = 0

    beetle_velocity = -4  # Negative value to move left
    butterfly_velocity = -7  # Negative value to move left
    # Load Beetle image
    beetle_surface = pygame.image.load(
        "CS50FinalProject/graphics/Beetle/dbeetle1.png"
    ).convert_alpha()

    # Create the rect for the Beetle
    beetle_rect = beetle_surface.get_rect(topleft=(screenwidth, 550))

    # Load Butterfly image
    butterfly_surface = pygame.image.load(
        "CS50FinalProject/graphics/Butterfly/dbutterfly1.png"
    ).convert_alpha()

    # Create the rect for the Butterfly
    butterfly_rect = butterfly_surface.get_rect(topleft=(screenwidth, 250))

    beetle_rect = beetle_surface.get_rect(topleft=(screenwidth, 550))
    butterfly_rect = butterfly_surface.get_rect(topleft=(screenwidth, 250))

    # Initialize timers for managing Beetle and Butterfly spawn delays

    player_velocity_y = 0
    gravity = 0.4  # Adjusted gravity for smoother motion
    jump_strength = -16  # Adjusted jump strength for better control
    max_velocity_y = 12  # Maximum vertical velocity
    is_jumping = False

    # Variables for snail speed increase
    time_accumulator = 0
    snail_respawn_delay = 2000  # Delay in milliseconds (e.g., 2000ms = 2 seconds)
    speed_increase_threshold = 6000  # Threshold in milliseconds, e.g., 5 seconds
    snail_speed = 3
    snail_timer = 0  # Initial speed of the snail

    bug_surface = pygame.image.load(
        "CS50FinalProject/graphics/Fly/fly1.png"
    ).convert_alpha()
    bug_x_pos = screenwidth + 300  # Initial x position of the bug, slightly off-screen
    bug_y_pos = 450  # Higher up than the snail
    bug_speed = 6  # Slightly faster than the snail

    # Create a rect for the bug
    bug_rect = bug_surface.get_rect(topleft=(bug_x_pos, bug_y_pos))

    # Set window title and icon
    pygame.display.set_caption("Duck Dash Debug! | CS50 Final Project")
    pygame.display.set_icon(
        pygame.image.load("CS50FinalProject/graphics/Player/player_walk1.png")
    )

    snail_spawn_delay = 1000
    fly_spawn_delay = 1500

    # Timers for managing spawn delays
    snail_spawn_timer = pygame.time.get_ticks()
    fly_spawn_timer = pygame.time.get_ticks()

    # Create a clock to control the fps of the game
    clock = pygame.time.Clock()

    # Set up the screen
    screen = pygame.display.set_mode((screenwidth, screenheight))

    # Load and scale images
    land_surface = pygame.transform.scale2x(
        pygame.image.load("CS50FinalProject/graphics/ground.png")
    ).convert_alpha()
    sky_surface = pygame.transform.scale2x(
        pygame.image.load("CS50FinalProject/graphics/sky.png")
    ).convert_alpha()

    # Load the snail image and set its x position
    snail_surface = pygame.image.load(
        "CS50FinalProject/graphics/snail/snail1.png"
    ).convert_alpha()
    snail_x_pos = screenwidth + 200
    snail_y_pos = 570
    player_x_pos = 100
    player_y_pos = 562
    player_width = 100  # the sprite's width
    player_height = 100
    land_x_pos = 0
    land_y_pos = 600
    ########################################################################

    # Load, resize, and position the text surface
    text_surface = pygame.image.load("CS50FinalProject/graphics/dash.png")
    original_width, original_height = text_surface.get_size()
    new_dimensions = (int(original_width * 1.2), int(original_height * 1.2))
    text_surface = pygame.transform.scale(text_surface, new_dimensions).convert_alpha()

    # Load, resize, and position the player surface
    player_surface = pygame.image.load(
        "CS50FinalProject/graphics/Player/player_walk1.png"
    )
    original_width, original_height = player_surface.get_size()
    player_surface_dimensions = (original_width // 3, original_height // 3)
    player_surface = pygame.transform.scale(
        player_surface, player_surface_dimensions
    ).convert_alpha()
    ########################################################################
    # Calculate the dimensions for the rect that has 50% of the player's surface area
    player_surface_area = player_surface_dimensions[0] * player_surface_dimensions[1]
    new_area = player_surface_area * 0.5
    new_width = math.sqrt(
        new_area * (player_surface_dimensions[0] / player_surface_dimensions[1])
    )
    new_height = new_area / new_width
    ########################################################################
    # Create the rect, initially with the top-left corner at (0, 0)
    player_rect = pygame.Rect(0, 0, new_width, new_height)

    # Set the center of the rect to the sprite's center position
    player_rect.center = (
        player_x_pos + player_surface_dimensions[0] // 2,
        player_y_pos + player_surface_dimensions[1] // 2,
    )
    ########################################################################
    # Load and resize music button images
    music_button_image_on = pygame.image.load("CS50FinalProject/media/on.png")
    music_button_image_off = pygame.image.load("CS50FinalProject/media/off.png")
    original_width, original_height = music_button_image_on.get_size()
    new_dimensions = (original_width // 7, original_height // 7)
    music_button_image_on = pygame.transform.scale(
        music_button_image_on, new_dimensions
    )
    music_button_image_off = pygame.transform.scale(
        music_button_image_off, new_dimensions
    ).convert_alpha()
    ########################################################################
    pause_button_image = pygame.image.load("CS50FinalProject/media/pause.png")
    original_width, original_height = pause_button_image.get_size()
    new_dimensions = (original_width // 7, original_height // 7)
    pause_button_image = pygame.transform.scale(
        pause_button_image, new_dimensions
    ).convert_alpha()
    ########################################################################
    # Set the rectangle for the pause button
    pause_button_rect = pause_button_image.get_rect(topleft=(screenwidth - 200, 0))

    # Pause state
    game_paused = False

    # Set the rectangle for the music button
    music_button_rect = music_button_image_on.get_rect(topleft=(screenwidth - 100, 0))

    # Load and play background music
    pygame.mixer.music.load("CS50FinalProject/media/music.mp3")
    pygame.mixer.music.play(-1)  # Loop the music

    player_surface_area = player_width * player_height

    # Calculate the dimensions for the rect that has 50% of the player's surface area
    new_area = player_surface_area * 0.5
    new_width = math.sqrt(new_area * (player_width / player_height))
    new_height = new_area / new_width

    # Create the rect, initially with the top-left corner at (0, 0)
    player_rect = pygame.Rect(0, 0, new_width, new_height)

    # Set the center of the rect to the sprite's center position
    player_rect.center = (player_x_pos, player_y_pos)

    snail_rect = snail_surface.get_rect(topleft=(snail_x_pos, snail_y_pos))
    land_rect = land_surface.get_rect(topleft=(land_x_pos, land_y_pos))
    text_rect = text_surface.get_rect(topleft=(590, 50))
    music_playing = True

    heart_surface = pygame.image.load(
        "CS50FinalProject/graphics/heart.png"
    ).convert_alpha()

    # Get the width of the heart surface
    heart_width = heart_surface.get_width()

    # Set the top left corner of the heart rect
    # X is random between 0 and screenwidth - heart_width
    # Y is set to 0 or slightly off-screen
    heart_x = random.randint(0, screenwidth - heart_width)
    heart_y = 0  # Or a negative value if you want it to appear falling down
    heart_rect = heart_surface.get_rect(topleft=(heart_x, heart_y))

    player_health = 3
    invulnerability_time = 3000  # 3 seconds in milliseconds
    last_hit_time = 0

    # Game loop
    ##########################################################################################
    running = True
    while running:
        # Get the time passed since last tick
        dt = clock.tick(60)
        time_accumulator += dt  # Add the delta time to the accumulator
        current_time = pygame.time.get_ticks()

        beetle_reset_delay = 15000 - (level * 1000)  # For example, 2 seconds
        butterfly_reset_delay = 20000 - (level * 1000)  # For example, 2 seconds
        heart_reset_delay = 5000 + (level * 1000)  # For example, 2 seconds
        if current_time - level_up_timer > level_up_interval:
            level += 1
            level_up_timer = current_time

        # Event handling
        for event in pygame.event.get():
            if (
                event.type == pygame.QUIT
                or event.type == pygame.KEYDOWN
                and event.key == pygame.K_ESCAPE
            ):
                pygame.quit()
                sys.exit()
            if event.type == pygame.MOUSEBUTTONDOWN:
                if music_button_rect.collidepoint(event.pos):
                    music_playing = not music_playing  # Toggle music state
                    if music_playing:
                        pygame.mixer.music.unpause()
                    else:
                        pygame.mixer.music.pause()
                if pause_button_rect.collidepoint(event.pos):
                    game_paused = not game_paused
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE or event.key == pygame.K_w:
                    # Check if the player can jump (less than 2 jumps)
                    if player_rect.bottom >= land_rect.top:
                        if jump_count < 2:
                            player_velocity_y = jump_strength  # Adjusted jump strength
                            is_jumping = True

        # Logic for changing the player sprite
        if is_jumping:
            player_surface = pygame.image.load(
                "CS50FinalProject/graphics/Player/player_jump.png"
            )
        else:
            player_surface = pygame.image.load(
                "CS50FinalProject/graphics/Player/player_walk1.png"
            )

        # Check for collisions and flash the screen
        if (
            player_rect.colliderect(snail_rect)
            or player_rect.colliderect(bug_rect)
            or player_rect.colliderect(beetle_rect)
            or player_rect.colliderect(butterfly_rect)
        ):
            # Play sound effect
            current_time = pygame.time.get_ticks()
            if current_time - last_hit_time > invulnerability_time:
                player_health -= 1
                last_hit_time = current_time

                quack_effect.play()

                if player_health == 0:
                    game_over_screen(screen)
                    break  # Exit the main game loop

        if player_rect.colliderect(heart_rect) and player_health < 3:
            player_health += 1
            heart_x = random.randint(0, screenwidth - heart_width)
            heart_y = 0
            heart_rect.topleft = (heart_x, heart_y)

        # Resize the player sprite
        player_surface_dimensions = (original_width // 3, original_height // 3)
        player_surface = pygame.transform.scale(
            player_surface, player_surface_dimensions
        ).convert_alpha()
        # Continuous key handling (a, s, d) for smooth movement
        keys = pygame.key.get_pressed()
        if keys[pygame.K_s]:
            gravity += 0.35  # Add 0.1 to gravity to increase it
        if keys[pygame.K_a]:
            player_rect.x -= 10
        if keys[pygame.K_d]:
            player_rect.x += 10

        # Don't update game elements if the game is paused
        if game_paused:
            continue

        # Update the player
        player_surface_rect = player_surface.get_rect(center=player_rect.center)
        player_rect.centery += player_velocity_y

        # Apply gravity
        player_velocity_y += gravity

        # Cap the vertical velocity to the maximum
        player_velocity_y = min(player_velocity_y, max_velocity_y)

        # Cap the player's maximum height to the top of the screen
        if player_rect.top < 0:
            player_rect.top = 0

        # Stop applying gravity when landing
        if player_rect.bottom >= land_rect.top:
            player_rect.bottom = land_rect.top
            is_jumping = False
            player_velocity_y = 0  # Reset the velocity
            gravity = 0.4  # Reset the gravity to its initial value
            jump_count = 0

        # Update the snail
        if time_accumulator > speed_increase_threshold:
            snail_speed += 0.5  # Increase the snail's speed
            time_accumulator = 0  # Reset the accumulator

        snail_rect.x -= snail_speed + (level / 1.5)
        if snail_rect.right < 0:
            if (
                snail_timer == 0
                or pygame.time.get_ticks() - snail_timer > snail_respawn_delay
            ):
                snail_timer = pygame.time.get_ticks()
                snail_rect.left = (
                    screenwidth  # Respawn the snail off the right side of the screen
                )

        bug_rect.x -= bug_speed + (level / 1.75)

        # Handle snail spawning
        if (
            snail_rect.right < 0
            and current_time - snail_spawn_timer > snail_spawn_delay
        ):
            snail_spawn_timer = current_time + random.randint(-500, 500)
            snail_rect.left = screenwidth  # Respawn the snail

        # Handle fly spawning
        if bug_rect.right < 0 and current_time - fly_spawn_timer > fly_spawn_delay:
            fly_spawn_timer = current_time + random.randint(-500, 500)
            bug_rect.left = screenwidth  # Respawn the fly

        # Reset bug's position if it moves off screen
        if bug_rect.right < 0:
            bug_rect.left = screenwidth

        # Respawn the beetle if it moves off-screen
        # Check if the level is high enough for the beetle to spawn

        # Move the beetle and butterfly
        beetle_rect.x -= 5 + (level / 2)
        butterfly_rect.x -= 7 + (level / 2)

        # Respawn the beetle and butterfly if they move off-screen
        # Check if the beetle has moved off the screen
        if beetle_rect.right < 0 and beetle_is_alive:
            if beetle_reset_timer is None:
                beetle_reset_timer = (
                    current_time  # Start the timer when it first moves off-screen
                )

            if current_time - beetle_reset_timer > beetle_reset_delay:
                beetle_rect.left = (
                    screenwidth  # Reset position to the right of the screen
                )
                beetle_reset_timer = None  # Reset the timer

        # Handle the butterfly
        if butterfly_rect.right < 0 and butterfly_is_alive:
            if butterfly_reset_timer is None:
                butterfly_reset_timer = (
                    current_time  # Start the timer when it first moves off-screen
                )

            if current_time - butterfly_reset_timer > butterfly_reset_delay:
                butterfly_rect.left = screenwidth
                butterfly_reset_timer = None  # Reset the timer

        # Respawn the heart if it moves off-screen
        if heart_rect.top > screenheight:
            if heart_reset_timer == 0:
                heart_reset_timer = current_time
            if current_time - heart_reset_timer > heart_reset_delay:
                heart_x = random.randint(0, screenwidth - heart_width)
                heart_y = 0
                heart_rect.topleft = (heart_x, heart_y)
                heart_reset_timer = 0

        heart_rect.y += 5 + (level / 2)

        # Draw the background elements
        screen.blit(sky_surface, (0, 0))
        screen.blit(land_surface, land_rect)

        # Draw the player
        screen.blit(player_surface, player_surface_rect)

        # Draw the snail
        screen.blit(snail_surface, snail_rect)

        # Draw the bug
        screen.blit(bug_surface, bug_rect)
        beetle_is_alive = False
        butterfly_is_alive = False

        if level >= beetle_start_level:
            screen.blit(beetle_surface, beetle_rect)
            beetle_is_alive = True

        if level >= butterfly_start_level:
            screen.blit(butterfly_surface, butterfly_rect)
            butterfly_is_alive = True

        # Draw the UI elements
        # Draw the music button
        if music_playing:
            screen.blit(music_button_image_on, music_button_rect.topleft)
        else:
            screen.blit(music_button_image_off, music_button_rect.topleft)

        # Draw the pause button
        screen.blit(pause_button_image, pause_button_rect.topleft)

        # Draw the text surface and its border
        screen.blit(text_surface, text_rect)

        health_text = f"Health: {player_health}"
        health_surface = font.render(health_text, True, (255, 255, 255))
        health_rect = health_surface.get_rect(topleft=(10, 10))
        screen.blit(health_surface, health_rect)
        
        heart_needed = False
        if player_health < 3:
            screen.blit(heart_surface, heart_rect)
            heart_needed = True

        level_text = f"Level: {level}"
        level_surface = font.render(level_text, True, (255, 255, 255))
        level_rect = level_surface.get_rect(topleft=(screenwidth - 200, 100))
        screen.blit(level_surface, level_rect)

        # Update the display
        pygame.display.update()


def game_over_screen(screen):
    font = pygame.font.Font(None, 48)
    message = font.render("GAME OVER! Play again? (Y/N)", True, (255, 0, 0))

    music = "CS50FinalProject\media\dbye.mp3"

    for i in range(1):
        pygame.mixer.music.load(music)
        pygame.mixer.music.play(1)
        pygame.time.delay(1000)

    message_rect = message.get_rect(
        center=(screen.get_width() // 2, screen.get_height() // 2)
    )

    screen.blit(message, message_rect)
    pygame.display.update()

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_y:
                    main_game(screen)  # Restart the game
                elif event.key == pygame.K_n:
                    pygame.quit()
                    sys.exit()


# When you call main_game, make sure to pass the screen object
if __name__ == "__main__":
    pygame.init()
    screen = pygame.display.set_mode((1600, 800))
    main_game(screen)
