import pygame
from landing_screen import show_landing_screen
from game import main_game


def main():
    pygame.init()
    screen = pygame.display.set_mode((500, 750))
    pygame.display.set_caption("Duck Dash Debug - CS50 Final Project")

    button_width = 138
    button_height = 76
    play_button_pos = (180, 190)  # (x, y) position for the "Play" button
    how_to_play_button_pos = (180, 300)  # (x, y) position for the "How to Play" button
    about_button_pos = (180, 425)  # (x, y) position for the "About" button
    music = "CS50FinalProject\media\dfast.mp3"
    background_image_path = "CS50FinalProject\media\ducklandplay.png"
    # Show the landing screen
    show_landing_screen(
        screen,
        main_game,
        music,
        background_image_path,
        play_button_pos,
        how_to_play_button_pos,
        about_button_pos,
        button_width,  # Button width
        button_height,  # Button height
    )

    # Start the main game loop
    # main_game(screen)  # Uncomment if the main game should start immediately after the landing screen

    pygame.quit()


if __name__ == "__main__":
    main()
