import pygame


def show_landing_screen(
    screen,
    start_game_callback,
    music,
    background_image_path,
    play_button_pos,
    how_to_play_button_pos,
    about_button_pos,
    button_width,
    button_height,
):
    # Load the background image
    background_image = load_image(background_image_path, alpha=False)

    # Start playing music
    pygame.mixer.music.load(music)
    pygame.mixer.music.play(-1)

    running = True

    # Create Rects for the buttons using the provided positions
    play_button_rect = pygame.Rect(play_button_pos, (button_width, button_height))
    how_to_play_button_rect = pygame.Rect(
        how_to_play_button_pos, (button_width, button_height)
    )
    about_button_rect = pygame.Rect(about_button_pos, (button_width, button_height))

    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.MOUSEBUTTONDOWN:
                if play_button_rect.collidepoint(event.pos):
                    start_game_callback(screen)  # Pass 'screen' to the callback
                    running = False
                elif how_to_play_button_rect.collidepoint(event.pos):
                    # Placeholder for 'How to Play' functionality
                    print("How to Play button pressed.")
                elif about_button_rect.collidepoint(event.pos):
                    # Placeholder for 'About' functionality
                    print("About button pressed.")

        # Draw the background image
        screen.blit(background_image, (0, 0))

        # Draw buttons
        draw_button(screen, "Play", play_button_rect.topleft, play_button_rect.size)
        draw_button(
            screen,
            "How to Play",
            how_to_play_button_rect.topleft,
            how_to_play_button_rect.size,
        )
        draw_button(screen, "About", about_button_rect.topleft, about_button_rect.size)

        # Update the screen
        pygame.display.flip()


def load_image(path, scale_x=1, scale_y=1, alpha=True):
    image = pygame.image.load(path)
    if scale_x != 1 or scale_y != 1:
        image = pygame.transform.scale(image, (scale_x, scale_y))
    if alpha:
        return image.convert_alpha()
    return image.convert()


def draw_button(screen, text, position, size, action=None):
    font_name = "CS50FinalProject\media\erainyhearts.ttf"
    font = pygame.font.Font(font_name, 26)

    # Create a separate surface for the button with per-pixel alpha
    button_surface = pygame.Surface(size, pygame.SRCALPHA)

    # Fill the button surface with your desired color and opacity
    button_surface.fill((0, 0, 0, 255))  # RGBA: (0, 0, 0) is black, 255 is full opacity

    # Draw the button border
    pygame.draw.rect(button_surface, (255, 255, 255), (0, 0, size[0], size[1]), 2)

    # Render the text
    text_surf = font.render(text, True, (255, 255, 255))
    text_rect = text_surf.get_rect(center=(size[0] // 2, size[1] // 2))

    # Blit text onto the button surface
    button_surface.blit(text_surf, text_rect)

    # Blit the button surface onto the main screen
    screen.blit(button_surface, position)

    # Return a rect for the button, for handling clicks
    return pygame.Rect(position, size)
